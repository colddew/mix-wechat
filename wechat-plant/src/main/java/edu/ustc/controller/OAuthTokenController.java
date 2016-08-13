package edu.ustc.controller;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.AuthorizeScope;
import edu.ustc.config.WechatConstants;
import edu.ustc.pojo.WechatOAuthToken;
import edu.ustc.service.OAuthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/oauthToken")
public class OAuthTokenController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthTokenController.class);

    @Autowired
    private OAuthTokenService oAuthTokenService;

    @RequestMapping(value = "/baseAuthorizeUrl", method = RequestMethod.GET)
    public String getBaseAuthorizeUrl(String appID, String redirectUri) {

        try {
            String authorizeUrl = oAuthTokenService.getAuthorizeUrl(appID, redirectUri, AuthorizeScope.BASE.getCode(),
                    WechatConstants.WECHAT_AUTHORIZE_STATE_BASE);
            logger.info("get base authorize url success, {}", authorizeUrl);
            return authorizeUrl;
        } catch (Exception e) {
            logger.error("get base authorize url error, {}" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/userInfoAuthorizeUrl", method = RequestMethod.GET)
    public String getUserInfoAuthorizeUrl(String appID, String redirectUri) {

        try {
            String authorizeUrl = oAuthTokenService.getAuthorizeUrl(appID, redirectUri, AuthorizeScope.USERINFO.getCode(),
                    WechatConstants.WECHAT_AUTHORIZE_STATE_USERINFO);
            logger.info("get userInfo authorize url success, {}", authorizeUrl);
            return authorizeUrl;
        } catch (Exception e) {
            logger.error("get userInfo authorize url error, {}", e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public WechatOAuthToken getWechatOAuthToken(String code, String state) {
        try {
            WechatOAuthToken wechatOAuthToken = oAuthTokenService.getWechatOAuthToken(code, state);
            logger.info("get wechat oauth token success, {}", JSON.toJSONString(wechatOAuthToken));
            return wechatOAuthToken;
        } catch (Exception e) {
            logger.error("get wechat oauth token error, {}", e.getMessage());
            return null;
        }
    }
}
