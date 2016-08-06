package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.WechatProperties;
import edu.ustc.pojo.WechatAccessToken;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@EnableScheduling
public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    private String appID;
    private String appSecret;
    private long expiryStartTime;
    private WechatAccessToken wechatAccessToken;

    @Autowired
    private WechatProperties wechatProperties;

    @PostConstruct
    public void init() {
        expiryStartTime = 0;
        wechatAccessToken = new WechatAccessToken();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void autoRefreshAccessToken() {
        if(needAutoRefreshAccessToken()) {
            try {
                WechatAccessToken accessToken = refreshAccessToken(appID, appSecret);
                logger.info("auto refresh access token success, {}", JSON.toJSONString(accessToken));
            } catch (Exception e) {
                logger.error("auto refresh access token error, {}", e.getMessage());
            }
        }
    }

    private boolean needAutoRefreshAccessToken() {

        if(StringUtils.isNotBlank(appID) && StringUtils.isNotBlank(appSecret)) {
            return true;
        }

        return false;
    }

    public WechatAccessToken refreshAccessToken(String appID, String appSecret) throws Exception {

        if (needRefreshAccessToken()) {

            String wechatAccessTokenUrl = getWechatAccessTokenUrl(appID, appSecret);
            String result = OkHttpUtils.synGetString(wechatAccessTokenUrl);
            WechatAccessToken accessToken = JSON.parseObject(result, WechatAccessToken.class);

            cache(appID, appSecret, accessToken);

            return accessToken;
        } else {
            return wechatAccessToken;
        }
    }

    private boolean needRefreshAccessToken() {

        if(StringUtils.isBlank(wechatAccessToken.getAccessToken())) {
            return true;
        }

        if(StringUtils.isNotBlank(wechatAccessToken.getAccessToken()) && StringUtils.isNotBlank(wechatAccessToken.getExpiry()) && expiryStartTime > 0) {

            long now = new Date().getTime();
            long expiry = Long.parseLong(wechatAccessToken.getExpiry());

            if(now - expiryStartTime > expiry * 1000) {
                return true;
            }
        }

        return false;
    }

    private String getWechatAccessTokenUrl(String appID, String appSecret) throws Exception {

        String wechatAccessTokenUrl = wechatProperties.getWechatAccessTokenUrl();
        wechatAccessTokenUrl = wechatAccessTokenUrl.replaceAll("#APPID#", appID);
        wechatAccessTokenUrl = wechatAccessTokenUrl.replaceAll("#APPSECRET#", appSecret);
        
        return wechatAccessTokenUrl;
    }

    private void cache(String appID, String appSecret, WechatAccessToken wechatAccessToken) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.expiryStartTime = new Date().getTime();
        this.wechatAccessToken = wechatAccessToken;
    }
}
