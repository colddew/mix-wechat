package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import edu.ustc.config.GlobalCode;
import edu.ustc.config.WechatConstants;
import edu.ustc.config.WechatException;
import edu.ustc.config.WechatProperties;
import edu.ustc.dto.VerificationRequest;
import edu.ustc.pojo.JsApiConfig;
import edu.ustc.pojo.WechatOAuthToken;
import edu.ustc.pojo.WechatUserInfo;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.*;

@Service
public class WechatPlantService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private OAuthTokenService oAuthTokenService;

    @Autowired
    private AccessTokenService accessTokenService;

    public String getSignature(VerificationRequest request) throws Exception {
        return encryptWithSHA1(concatVerificationInfo(request));
    }

    private String concatVerificationInfo(VerificationRequest request) throws Exception {

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

        String jsApiTicketUrl = StringUtils.replaceEach(wechatProperties.getJsApiTicketUrl(), new String[]{"#ACCESS_TOKEN#"},
                new String[]{accessTokenService.getWechatAccessToken().getAccessToken()});

        String result = OkHttpUtils.synGetString(jsApiTicketUrl);
        JsApiConfig jsApiConfig = JSON.parseObject(result, JsApiConfig.class);
        if(!GlobalCode.CODE_OK.getCode().equals(jsApiConfig.getErrorCode())) {
            throw new WechatException(jsApiConfig.getErrorCode(), jsApiConfig.getErrorMessage());
        }

        supplement(jsApiConfig, url);

        return jsApiConfig;
    }

    private void supplement(JsApiConfig jsApiConfig, String url) throws Exception {

        if(StringUtils.isNotBlank(jsApiConfig.getExpiry())) {

            Date expiryStartTime = new Date();
            String timestamp = String.valueOf(expiryStartTime.getTime());
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
                put(ticket, "jsapi_ticket");
                put(random, "noncestr");
                put(timestamp, "timestamp");
                put(url, "url");
            }
        };

        StringBuilder sb = new StringBuilder();
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sb.append(entry.getValue()).append("=").append(entry.getKey()).append("&");
        }

        return sb.substring(0, sb.length() - 1);
    }
}
