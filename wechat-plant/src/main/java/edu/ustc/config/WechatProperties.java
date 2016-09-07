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
    private String oAuthTokenUrl;

    @Value("${wechat.oauth.refresh.token.url}")
    private String refreshOAuthTokenUrl;

    @Value("${wechat.oauth.userinfo.url}")
    private String oAuthUserInfoUrl;

    @Value("${wechat.oauth.check.token.url}")
    private String checkOAuthTokenUrl;

    @Value("${wechat.jsapi.ticket.url}")
    private String jsApiTicketUrl;

    @Value("${wechat.menu.create.url}")
    private String createMenuUrl;

    @Value("${wechat.menu.get.url}")
    private String getMenuUrl;

    @Value("${wechat.menu.delete.url}")
    private String deleteMenuUrl;

    @Value("${wechat.material.batchget.url}")
    private String batchGetMaterialUrl;

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

    public String getOAuthTokenUrl() {
        return oAuthTokenUrl;
    }

    public void setOAuthTokenUrl(String oAuthTokenUrl) {
        this.oAuthTokenUrl = oAuthTokenUrl;
    }

    public String getRefreshOAuthTokenUrl() {
        return refreshOAuthTokenUrl;
    }

    public void setRefreshOAuthTokenUrl(String refreshOAuthTokenUrl) {
        this.refreshOAuthTokenUrl = refreshOAuthTokenUrl;
    }

    public String getOAuthUserInfoUrl() {
        return oAuthUserInfoUrl;
    }

    public void setOAuthUserInfoUrl(String oAuthUserInfoUrl) {
        this.oAuthUserInfoUrl = oAuthUserInfoUrl;
    }

    public String getCheckOAuthTokenUrl() {
        return checkOAuthTokenUrl;
    }

    public void setCheckOAuthTokenUrl(String checkOAuthTokenUrl) {
        this.checkOAuthTokenUrl = checkOAuthTokenUrl;
    }

    public String getJsApiTicketUrl() {
        return jsApiTicketUrl;
    }

    public void setJsApiTicketUrl(String jsApiTicketUrl) {
        this.jsApiTicketUrl = jsApiTicketUrl;
    }

    public String getCreateMenuUrl() {
        return createMenuUrl;
    }

    public void setCreateMenuUrl(String createMenuUrl) {
        this.createMenuUrl = createMenuUrl;
    }

    public String getGetMenuUrl() {
        return getMenuUrl;
    }

    public void setGetMenuUrl(String getMenuUrl) {
        this.getMenuUrl = getMenuUrl;
    }

    public String getDeleteMenuUrl() {
        return deleteMenuUrl;
    }

    public void setDeleteMenuUrl(String deleteMenuUrl) {
        this.deleteMenuUrl = deleteMenuUrl;
    }

    public String getBatchGetMaterialUrl() {
        return batchGetMaterialUrl;
    }

    public void setBatchGetMaterialUrl(String batchGetMaterialUrl) {
        this.batchGetMaterialUrl = batchGetMaterialUrl;
    }
}
