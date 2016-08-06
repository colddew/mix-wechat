package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.WechatProperties;
import edu.ustc.pojo.WechatAccessToken;
import edu.ustc.utils.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    @Autowired
    private WechatProperties wechatProperties;

    private WechatAccessToken wechatAccessToken;

    public void init() {

    }

    public WechatAccessToken refreshAccessToken(String appID, String appSecret) throws Exception {

        String wechatAccessTokenUrl = getWechatAccessTokenUrl(appID, appSecret);

        String result = OkHttpUtils.synGetString(wechatAccessTokenUrl);

        return JSON.parseObject(result, WechatAccessToken.class);
    }

    private String getWechatAccessTokenUrl(String appID, String appSecret) throws Exception {

        String wechatAccessTokenUrl = wechatProperties.getWechatAccessTokenUrl();
        wechatAccessTokenUrl = wechatAccessTokenUrl.replaceAll("#APPID#", appID);
        wechatAccessTokenUrl = wechatAccessTokenUrl.replaceAll("#APPSECRET#", appSecret);
        
        return wechatAccessTokenUrl;
    }
}
