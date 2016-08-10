package edu.ustc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class WechatProperties {

    @Value("${wechat.sandbox.appid}")
    private String appID;

    @Value("${wechat.sandbox.appsecret}")
    private String appSecret;

    @Value("${wechat.access.token.url}")
    private String wechatAccessTokenUrl;

    @Value("${wechat.clear.quota.url}")
    private String clearQuotaUrl;

    @Value("${wechat.oauth.authorize.url}")
    private String authorizeUrl;

    @Value("${wechat.oauth.token.url}")
    private String oauthTokenUrl;

    @Value("${wechat.oauth.refresh.token.url}")
    private String refreshOauthTokenUrl;

    @Value("${wechat.oauth.userinfo.url}")
    private String oauthUserInfoUrl;

    @Value("${wechat.oauth.check.token.url}")
    private String checkOauthTokenUrl;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getWechatAccessTokenUrl() {
        return wechatAccessTokenUrl;
    }

    public void setWechatAccessTokenUrl(String wechatAccessTokenUrl) {
        this.wechatAccessTokenUrl = wechatAccessTokenUrl;
    }

    public String getClearQuotaUrl() {
        return clearQuotaUrl;
    }

    public void setClearQuotaUrl(String clearQuotaUrl) {
        this.clearQuotaUrl = clearQuotaUrl;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getOauthTokenUrl() {
        return oauthTokenUrl;
    }

    public void setOauthTokenUrl(String oauthTokenUrl) {
        this.oauthTokenUrl = oauthTokenUrl;
    }

    public String getRefreshOauthTokenUrl() {
        return refreshOauthTokenUrl;
    }

    public void setRefreshOauthTokenUrl(String refreshOauthTokenUrl) {
        this.refreshOauthTokenUrl = refreshOauthTokenUrl;
    }

    public String getOauthUserInfoUrl() {
        return oauthUserInfoUrl;
    }

    public void setOauthUserInfoUrl(String oauthUserInfoUrl) {
        this.oauthUserInfoUrl = oauthUserInfoUrl;
    }

    public String getCheckOauthTokenUrl() {
        return checkOauthTokenUrl;
    }

    public void setCheckOauthTokenUrl(String checkOauthTokenUrl) {
        this.checkOauthTokenUrl = checkOauthTokenUrl;
    }
}
