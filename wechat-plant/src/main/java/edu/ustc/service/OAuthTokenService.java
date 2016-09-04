package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.WechatGlobalCode;
import edu.ustc.config.WechatException;
import edu.ustc.config.WechatProperties;
import edu.ustc.pojo.WechatOAuthToken;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.replaceEach;

@Service
public class OAuthTokenService {

    @Autowired
    private WechatProperties wechatProperties;
    
    public String getAuthorizeUrl(String appID, String redirectUri, String scope, String state) {

        String authorizeUrl = wechatProperties.getAuthorizeUrl();
        String authorizeAppID = StringUtils.isNotBlank(appID) ? appID : wechatProperties.getAppID();

        return replaceEach(authorizeUrl, new String[]{"#APPID#", "#REDIRECT_URI#", "#SCOPE#", "#STATE#"},
                new String[]{authorizeAppID, redirectUri, scope, state});
    }

    public WechatOAuthToken getWechatOAuthToken(String code, String state) throws Exception {

        checkState(state);

        String oAuthTokenUrl = replaceEach(wechatProperties.getOAuthTokenUrl(), new String[]{"#APPID#", "#APPSECRET#", "#CODE#"},
                new String[]{wechatProperties.getAppID(), wechatProperties.getAppSecret(), code});

        String result = OkHttpUtils.synGetString(oAuthTokenUrl);
        WechatOAuthToken oAuthToken = JSON.parseObject(result, WechatOAuthToken.class);
        if(StringUtils.isNotBlank(oAuthToken.getErrorCode())) {
            throw new WechatException(oAuthToken.getErrorCode(), oAuthToken.getErrorMessage());
        }

        //TODO store oauth token and refresh token

        return oAuthToken;
    }

    private void checkState(String state) {
        // do nothing
    }

    public WechatOAuthToken refreshOAuthToken(String refreshToken) throws Exception {

        String refreshOAuthTokenUrl = StringUtils.replaceEach(wechatProperties.getRefreshOAuthTokenUrl(), new String[]{"#APPID#", "#REFRESH_TOKEN#"},
                new String[]{wechatProperties.getAppID(), refreshToken});

        String result = OkHttpUtils.synGetString(refreshOAuthTokenUrl);
        WechatOAuthToken oAuthToken = JSON.parseObject(result, WechatOAuthToken.class);
        if(StringUtils.isNotBlank(oAuthToken.getErrorCode())) {
            throw new WechatException(oAuthToken.getErrorCode(), oAuthToken.getErrorMessage());
        }

        return oAuthToken;
    }

    public WechatOAuthToken checkOAuthToken(String oAuthToken, String openID) throws Exception {

        String checkOAuthTokenUrl = StringUtils.replaceEach(wechatProperties.getCheckOAuthTokenUrl(), new String[]{"#OAUTH_TOKEN#", "#OPENID#"},
                new String[]{oAuthToken, openID});

        String result = OkHttpUtils.synGetString(checkOAuthTokenUrl);
        WechatOAuthToken token = JSON.parseObject(result, WechatOAuthToken.class);
        if(!WechatGlobalCode.CODE_OK.getCode().equals(token.getErrorCode())) {
            throw new WechatException(token.getErrorCode(), token.getErrorMessage());
        }

        return token;
    }
}
