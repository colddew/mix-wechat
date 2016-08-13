package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.WechatException;
import edu.ustc.config.WechatProperties;
import edu.ustc.pojo.WechatOAuthToken;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthTokenService {

    @Autowired
    private WechatProperties wechatProperties;
    
    public String getAuthorizeUrl(String appID, String redirectUri, String scope, String state) {

        String authorizeUrl = wechatProperties.getAuthorizeUrl();
        String authorizeAppID = StringUtils.isNotBlank(appID) ? appID : wechatProperties.getAppID();

        return StringUtils.replaceEach(authorizeUrl, new String[]{"#APPID#", "#REDIRECT_URI#", "#SCOPE#", "#STATE#"},
                new String[]{authorizeAppID, redirectUri, scope, state});
    }

    public WechatOAuthToken getWechatOAuthToken(String code, String state) throws Exception {

        String oauthTokenUrl = StringUtils.replaceEach(wechatProperties.getOauthTokenUrl(), new String[]{"#APPID#", "#APPSECRET#", "#CODE#"},
                new String[]{wechatProperties.getAppID(), wechatProperties.getAppSecret(), code});

        String result = OkHttpUtils.synGetString(oauthTokenUrl);
        WechatOAuthToken oAuthToken = JSON.parseObject(result, WechatOAuthToken.class);
        if(StringUtils.isNotBlank(oAuthToken.getErrorCode())) {
            throw new WechatException(oAuthToken.getErrorCode(), oAuthToken.getErrorMessage());
        }

        return oAuthToken;
    }
}
