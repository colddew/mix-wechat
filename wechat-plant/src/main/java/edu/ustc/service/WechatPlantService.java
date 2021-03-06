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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WechatPlantService {

    private static final Logger logger = LoggerFactory.getLogger(WechatPlantService.class);

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private OAuthTokenService oAuthTokenService;

    @Autowired
    private AccessTokenService accessTokenService;

    private Map<String, JsApiConfig> jsApiConfigMap = new ConcurrentHashMap<>();

    private WechatMaterialList wechatMaterialList;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void autoRefreshWechatMaterialList() {
        if(needRefreshWechatMaterialList()) {
            try {
                getWechatNewsItemList();
                logger.info("auto refresh wechat material list success");
            } catch (Exception e) {
                logger.error("auto refresh wechat material list error, {}", e.getMessage());
            }
        }
    }

    public boolean checkSignature(VerificationRequest request) throws Exception {
        if(getSignature(request).equalsIgnoreCase(request.getSignature())) {
            return true;
        } else {
            return false;
        }
    }

    private String getSignature(VerificationRequest request) throws Exception {
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
            if(!WechatPublicPlatformCode.CODE_OK.getCode().equals(jsApiConfig.getErrorCode())) {
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
        if(!WechatPublicPlatformCode.CODE_OK.getCode().equals(object.getString("errcode"))) {
            throw new WechatException(object.getString("errcode"), object.getString("errmsg"));
        }

        return object.getString("errmsg");
    }

    private String assembleMenu() {

        WechatSubMenu garden = new WechatSubMenu();
        garden.setType(EventType.VIEW.getCode());
        garden.setName(MenuBrief.VIEW_PLANT_GARDEN.getDescription());
        garden.setUrl(MenuBrief.VIEW_PLANT_GARDEN.getUrl());

        WechatSubMenu balcony = new WechatSubMenu();
        balcony.setType(EventType.VIEW.getCode());
        balcony.setName(MenuBrief.VIEW_PLANT_BALCONY.getDescription());
        balcony.setUrl(MenuBrief.VIEW_PLANT_BALCONY.getUrl());

        WechatSubMenu link = new WechatSubMenu();
        link.setType(EventType.VIEW.getCode());
        link.setName(MenuBrief.VIEW_PLANT_LINK.getDescription());
        link.setUrl(MenuBrief.VIEW_PLANT_LINK.getUrl());

        WechatSubMenu location = new WechatSubMenu();
        location.setType(EventType.VIEW.getCode());
        location.setName(MenuBrief.VIEW_PLANT_ABOUT.getDescription());
        location.setUrl(MenuBrief.VIEW_PLANT_ABOUT.getUrl());

        List<WechatSubMenu> shareSubMenuList = new ArrayList<>();
        shareSubMenuList.add(garden);
        shareSubMenuList.add(balcony);

        List<WechatSubMenu> moreSubMenuList = new ArrayList<>();
        moreSubMenuList.add(link);
        moreSubMenuList.add(location);

        WechatSubMenu experience = new WechatSubMenu();
        experience.setType(EventType.CLICK.getCode());
        experience.setName(MenuBrief.CLICK_PLANT_EXPERIENCE.getDescription());
        experience.setKey(MenuBrief.CLICK_PLANT_EXPERIENCE.getCode());

        WechatSubMenu share = new WechatSubMenu();
        share.setName(MenuBrief.CLICK_PLANT_SHARE.getDescription());
        share.setSubButton(shareSubMenuList);

        WechatSubMenu more = new WechatSubMenu();
        more.setName(MenuBrief.CLICK_PLANT_MORE.getDescription());
        more.setSubButton(moreSubMenuList);

        List<WechatSubMenu> menuList = new ArrayList<>();
        menuList.add(experience);
        menuList.add(share);
        menuList.add(more);

        WechatMenu menu = new WechatMenu();
        menu.setButton(menuList);

        return JSON.toJSONString(menu);
    }

    private String assembleTestMenu() {

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

    public WechatMaterialList getWechatMaterialList(String json) throws Exception {

        if(needRefreshWechatMaterialList()) {

            String batchGetMaterialUrl = StringUtils.replaceEach(wechatProperties.getBatchGetMaterialUrl(), new String[]{"#ACCESS_TOKEN#"},
                    new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

            String result = OkHttpUtils.synPostJson(batchGetMaterialUrl, json);
            WechatMaterialList wechatMaterialList = JSON.parseObject(result, WechatMaterialList.class);
            if(StringUtils.isNotBlank(wechatMaterialList.getErrorCode())) {
                throw new WechatException(wechatMaterialList.getErrorCode(), wechatMaterialList.getErrorMessage());
            }

            supplement(wechatMaterialList);
            cache(wechatMaterialList);

            return wechatMaterialList;
        }

        return wechatMaterialList;
    }

    private boolean needRefreshWechatMaterialList() {

        if(null !=  wechatMaterialList) {

            long now = Instant.now().getEpochSecond();
            long refreshTime = wechatMaterialList.getRefreshTime();

            if(now - refreshTime < WechatConstants.WECHAT_COMMON_REFRESH_PERIOD_SECONDS) {
                return false;
            }
        }

        return true;
    }

    private void supplement(WechatMaterialList wechatMaterialList) {
        wechatMaterialList.setRefreshTime(Instant.now().getEpochSecond());
    }

    //TODO store the data to persistent cache may be the best choice, otherwise we will get "reach max api daily quota limit hint: [xxx]" exception soon
    private void cache(WechatMaterialList wechatMaterialList) {
        this.wechatMaterialList = wechatMaterialList;
    }

    public List<WechatNewsItem> getWechatNewsItemList() throws Exception {

        List<WechatNewsItem> list = new ArrayList<>();

        WechatMaterialList wechatMaterialList = getWechatMaterialList(assembleWechatNewsItemListRequest());
        if(null !=  wechatMaterialList) {

            List<WechatMaterialItem> wechatMaterialItemList = wechatMaterialList.getItem();
            if(!CollectionUtils.isEmpty(wechatMaterialItemList)) {

                for(WechatMaterialItem wechatMaterialItem : wechatMaterialItemList) {

                    WechatNewsContent wechatNewsContent = wechatMaterialItem.getContent();
                    if(null != wechatNewsContent) {

                        List<WechatNewsItem> wechatNewsItemList = wechatNewsContent.getNewsItems();
                        if(!CollectionUtils.isEmpty(wechatNewsItemList)) {

                            for(WechatNewsItem wechatNewsItem : wechatNewsItemList) {
                                if(checkWechatNewsItem(wechatNewsItem)) {
                                    list.add(wechatNewsItem);
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    private String assembleWechatNewsItemListRequest() {
        return "{\"type\":\"" + MessageType.news.name() + "\", " +
                "\"offset\":" + wechatProperties.getBatchGetMaterialOffset() + ", " +
                "\"count\":" + wechatProperties.getBatchGetMaterialCount() + "}";
    }

    private int getBatchGetMaterialOffset() {
        try {
            String countMaterialUrl = StringUtils.replaceEach(wechatProperties.getCountMaterialUrl(), new String[]{"#ACCESS_TOKEN#"},
                    new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

            String result = OkHttpUtils.synGetString(countMaterialUrl);
            WechatMaterialCount wechatMaterialCount = JSON.parseObject(result, WechatMaterialCount.class);
            if(StringUtils.isNotBlank(wechatMaterialList.getErrorCode())) {
                return wechatProperties.getBatchGetMaterialOffset();
            } else {
                return wechatMaterialCount.getNewsCount() > 0 ? wechatMaterialCount.getNewsCount() - 1 : wechatProperties.getBatchGetMaterialOffset();
            }
        } catch (Exception e) {
            return wechatProperties.getBatchGetMaterialOffset();
        }
    }

    private boolean checkWechatNewsItem(WechatNewsItem wechatNewsItem) {

        if(StringUtils.isNotBlank(wechatNewsItem.getTitle())
                && StringUtils.isNotBlank(wechatNewsItem.getDigest())
                && StringUtils.isNotBlank(wechatNewsItem.getThumbUrl())
                && StringUtils.isNotBlank(wechatNewsItem.getUrl())) {
            return true;
        }

        return false;
    }
}
