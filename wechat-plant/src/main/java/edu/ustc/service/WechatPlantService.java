package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import edu.ustc.config.*;
import edu.ustc.dto.VerificationRequest;
import edu.ustc.pojo.*;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WechatPlantService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private OAuthTokenService oAuthTokenService;

    @Autowired
    private AccessTokenService accessTokenService;

    private Map<String, JsApiConfig> jsApiConfigMap = new ConcurrentHashMap<>();

    public String getSignature(VerificationRequest request) throws Exception {
        return encryptWithSHA1(concatVerificationInfo(request));
    }

    private static String concatVerificationInfo(VerificationRequest request) throws Exception {

        ArrayList<String> list = Lists.newArrayList(WechatConstants.WECHAT_PLANT_TOKEN, Strings.nullToEmpty(request.getTimestamp()), Strings.nullToEmpty(request.getNonce()));
        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        for(String str : list) {
            sb.append(str);
        }

        return sb.toString();
    }

    private String encryptWithSHA1(String verificationInfo) throws Exception {
        HashFunction hashFunction = Hashing.sha1();
        return hashFunction.hashString(verificationInfo, Charset.defaultCharset()).toString();
    }

    public WechatUserInfo getWechatUserInfo(String oAuthToken, String openID) throws Exception {

        String oAuthUserInfoUrl = StringUtils.replaceEach(wechatProperties.getOAuthUserInfoUrl(), new String[]{"#OAUTH_TOKEN#", "#OPENID#"},
                new String[]{oAuthToken, openID});

        String result = OkHttpUtils.synGetString(oAuthUserInfoUrl);
        WechatUserInfo userInfo = JSON.parseObject(result, WechatUserInfo.class);
        if(StringUtils.isNotBlank(userInfo.getErrorCode())) {
            throw new WechatException(userInfo.getErrorCode(), userInfo.getErrorMessage());
        }

        //TODO store user info

        return userInfo;
    }

    public WechatUserInfo getUserInfo(String code, String state) throws Exception {

        WechatOAuthToken oAuthToken = oAuthTokenService.getWechatOAuthToken(code, state);
        oAuthTokenService.checkOAuthToken(oAuthToken.getOAuthToken(), oAuthToken.getOpenID());

        return getWechatUserInfo(oAuthToken.getOAuthToken(), oAuthToken.getOpenID());
    }

    public JsApiConfig getJsApiConfig(String url) throws Exception {

        if(needRefreshJsApiConfig(url)) {

            String jsApiTicketUrl = StringUtils.replaceEach(wechatProperties.getJsApiTicketUrl(), new String[]{"#ACCESS_TOKEN#"},
                    new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

            String result = OkHttpUtils.synGetString(jsApiTicketUrl);
            JsApiConfig jsApiConfig = JSON.parseObject(result, JsApiConfig.class);
            if(!WechatGlobalCode.CODE_OK.getCode().equals(jsApiConfig.getErrorCode())) {
                throw new WechatException(jsApiConfig.getErrorCode(), jsApiConfig.getErrorMessage());
            }

            supplement(jsApiConfig, url);
            cache(jsApiConfig, url);

            return jsApiConfig;
        }

        return jsApiConfigMap.get(url);
    }

    private boolean needRefreshJsApiConfig(String url) throws Exception {

        if(StringUtils.isNotBlank(url)) {

            if(jsApiConfigMap.containsKey(url)) {

                JsApiConfig jsApiConfig = jsApiConfigMap.get(url);

                long now = new Date().getTime();
                long expiryStartTime = jsApiConfig.getExpiryStartTime().getTime();
                long expiry = Long.parseLong(jsApiConfig.getExpiry()) * 1000;

                if(now - expiryStartTime > expiry) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    private void supplement(JsApiConfig jsApiConfig, String url) throws Exception {

        if(StringUtils.isNotBlank(jsApiConfig.getExpiry())) {

            Date expiryStartTime = new Date();
            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
            String random = RandomStringUtils.randomAlphanumeric(16);

            jsApiConfig.setExpiryStartTime(expiryStartTime);
            jsApiConfig.setExpiryEndTime(DateUtils.addSeconds(expiryStartTime, Integer.parseInt(jsApiConfig.getExpiry())));
            jsApiConfig.setAppID(wechatProperties.getAppID());
            jsApiConfig.setTimestamp(timestamp);
            jsApiConfig.setRandom(random);
            jsApiConfig.setSignature(encryptWithSHA1(concatJsApiConfig(jsApiConfig.getTicket(), random, timestamp, url)));
        }
    }

    private String concatJsApiConfig(String ticket, String random, String timestamp, String url) throws Exception {

        Map<String, String> map = new TreeMap<String, String>() {
            {
                put("jsapi_ticket", ticket);
                put("noncestr", random);
                put("timestamp", timestamp);
                put("url", url);
            }
        };

        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return sb.substring(0, sb.length() - 1);
    }

    private void cache(JsApiConfig jsApiConfig, String url) throws Exception {
        jsApiConfigMap.put(url, jsApiConfig);
    }

    public String createMenu() throws Exception {

        String createMenuUrl = StringUtils.replaceEach(wechatProperties.getCreateMenuUrl(), new String[]{"#ACCESS_TOKEN#"},
                new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

        String result = OkHttpUtils.synPostJson(createMenuUrl, assembleMenu());
        JSONObject object = JSON.parseObject(result);
        if(!WechatGlobalCode.CODE_OK.getCode().equals(object.getString("errcode"))) {
            throw new WechatException(object.getString("errcode"), object.getString("errmsg"));
        }

        return object.getString("errmsg");
    }

    private String assembleMenu() {

        WechatSubMenu subMenu1 = new WechatSubMenu();
        subMenu1.setType(EventType.CLICK.getCode());
        subMenu1.setName(MenuBrief.CLICK_DAILY_RECOMMEND.getDescription());
        subMenu1.setKey(MenuBrief.CLICK_DAILY_RECOMMEND.getCode());

        WechatSubMenu subMenu2 = new WechatSubMenu();
        subMenu2.setType(EventType.VIEW.getCode());
        subMenu2.setName(MenuBrief.VIEW_SEARCH.getDescription());
        subMenu2.setUrl(MenuBrief.VIEW_SEARCH.getUrl());

        WechatSubMenu subMenu3 = new WechatSubMenu();
        subMenu3.setType(EventType.VIEW.getCode());
        subMenu3.setName(MenuBrief.VIEW_VIDEO.getDescription());
        subMenu3.setUrl(MenuBrief.VIEW_VIDEO.getUrl());

        WechatSubMenu subMenu4 = new WechatSubMenu();
        subMenu4.setType(EventType.CLICK.getCode());
        subMenu4.setName(MenuBrief.CLICK_STAR.getDescription());
        subMenu4.setKey(MenuBrief.CLICK_STAR.getCode());

        WechatSubMenu subMenu5 = new WechatSubMenu();
        subMenu5.setType(EventType.SCANCODE_PUSH.getCode());
        subMenu5.setName(MenuBrief.SCANCODE_PUSH_SCANCODE.getDescription());
        subMenu5.setKey(MenuBrief.SCANCODE_PUSH_SCANCODE.getCode());

        WechatSubMenu subMenu6 = new WechatSubMenu();
        subMenu6.setType(EventType.SCANCODE_WAITMSG.getCode());
        subMenu6.setName(MenuBrief.SCANCODE_WAITMSG_SCANCODE.getDescription());
        subMenu6.setKey(MenuBrief.SCANCODE_WAITMSG_SCANCODE.getCode());

        WechatSubMenu subMenu7 = new WechatSubMenu();
        subMenu7.setType(EventType.PIC_SYSPHOTO.getCode());
        subMenu7.setName(MenuBrief.PIC_SYSPHOTO_PHOTO.getDescription());
        subMenu7.setKey(MenuBrief.PIC_SYSPHOTO_PHOTO.getCode());

        WechatSubMenu subMenu8 = new WechatSubMenu();
        subMenu8.setType(EventType.PIC_PHOTO_OR_ALBUM.getCode());
        subMenu8.setName(MenuBrief.PIC_PHOTO_OR_ALBUM_PHOTO.getDescription());
        subMenu8.setKey(MenuBrief.PIC_PHOTO_OR_ALBUM_PHOTO.getCode());

        WechatSubMenu subMenu9 = new WechatSubMenu();
        subMenu9.setType(EventType.PIC_WEIXIN.getCode());
        subMenu9.setName(MenuBrief.PIC_WEIXIN_ALBUM.getDescription());
        subMenu9.setKey(MenuBrief.PIC_WEIXIN_ALBUM.getCode());

        WechatSubMenu subMenu10 = new WechatSubMenu();
        subMenu10.setType(EventType.LOCATION_SELECT.getCode());
        subMenu10.setName(MenuBrief.LOCATION_SELECT_LOCATION.getDescription());
        subMenu10.setKey(MenuBrief.LOCATION_SELECT_LOCATION.getCode());

        WechatSubMenu subMenu11 = new WechatSubMenu();
        subMenu11.setType(EventType.MEDIA_ID.getCode());
        subMenu11.setName(MenuBrief.MEDIA_ID_PICTURE.getDescription());
        subMenu11.setMediaId(MenuBrief.MEDIA_ID_PICTURE.getCode());

        WechatSubMenu subMenu12 = new WechatSubMenu();
        subMenu12.setType(EventType.VIEW_LIMITED.getCode());
        subMenu12.setName(MenuBrief.VIEW_LIMITED_MESSAGE.getDescription());
        subMenu12.setMediaId(MenuBrief.VIEW_LIMITED_MESSAGE.getCode());

        List<WechatSubMenu> subMenuList1 = new ArrayList<>();
        subMenuList1.add(subMenu1);
        subMenuList1.add(subMenu2);
        subMenuList1.add(subMenu3);
        subMenuList1.add(subMenu4);

        List<WechatSubMenu> subMenuList2 = new ArrayList<>();
        subMenuList2.add(subMenu5);
        subMenuList2.add(subMenu6);
        subMenuList2.add(subMenu7);
        subMenuList2.add(subMenu8);
        subMenuList2.add(subMenu9);

        List<WechatSubMenu> subMenuList3 = new ArrayList<>();
        subMenuList3.add(subMenu10);
//        subMenuList3.add(subMenu11);      // need input existed wechat resource id
//        subMenuList3.add(subMenu12);

        WechatSubMenu menu1 = new WechatSubMenu();
        menu1.setName("点击/跳转");
        menu1.setSubButton(subMenuList1);

        WechatSubMenu menu2 = new WechatSubMenu();
        menu2.setName("扫码/发图");
        menu2.setSubButton(subMenuList2);

        WechatSubMenu menu3 = new WechatSubMenu();
        menu3.setName("位置/素材");
        menu3.setSubButton(subMenuList3);

        List<WechatSubMenu> menuList = new ArrayList<>();
        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);

        WechatMenu menu = new WechatMenu();
        menu.setButton(menuList);

        return JSON.toJSONString(menu);
    }

    public JSONObject getMenu() throws Exception {

        String getMenuUrl = StringUtils.replaceEach(wechatProperties.getGetMenuUrl(), new String[]{"#ACCESS_TOKEN#"},
                new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

        String result = OkHttpUtils.synGetString(getMenuUrl);

        return JSON.parseObject(result);
    }
}
