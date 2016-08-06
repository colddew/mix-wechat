package edu.ustc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class WechatProperties {

    @Value("${wechat.access.token.url}")
    private String wechatAccessTokenUrl;

    public String getWechatAccessTokenUrl() {
        return wechatAccessTokenUrl;
    }

    public void setWechatAccessTokenUrl(String wechatAccessTokenUrl) {
        this.wechatAccessTokenUrl = wechatAccessTokenUrl;
    }
}
