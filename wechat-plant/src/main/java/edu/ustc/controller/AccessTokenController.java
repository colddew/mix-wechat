package edu.ustc.controller;

import com.alibaba.fastjson.JSON;
import edu.ustc.pojo.WechatAccessToken;
import edu.ustc.service.AccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/accessToken")
public class AccessTokenController {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenController.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public WechatAccessToken refreshAccessToken(String appID, String appSecret) {

        try {
            WechatAccessToken accessToken = accessTokenService.refreshAccessToken(appID, appSecret);
            logger.info("manual refresh access token success, {}", JSON.toJSONString(accessToken));
            return accessToken;
        } catch (Exception e) {
            logger.error("manual refresh access token error, {}" + e.getMessage());
            return null;
        }
    }
}
