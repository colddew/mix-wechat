package edu.ustc.service;

import edu.ustc.config.WechatProperties;
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
}
