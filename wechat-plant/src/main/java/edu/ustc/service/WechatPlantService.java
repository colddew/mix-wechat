package edu.ustc.service;

import com.alibaba.fastjson.JSON;
import edu.ustc.config.WechatException;
import edu.ustc.config.WechatProperties;
import edu.ustc.pojo.WechatOAuthToken;
import edu.ustc.pojo.WechatUserInfo;
import edu.ustc.utils.OkHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatPlantService {

    @Autowired
    private WechatProperties wechatProperties;

    @Autowired
    private OAuthTokenService oAuthTokenService;

    public WechatUserInfo getWechatUserInfo(String oAuthToken, String openID) throws Exception {

        String oAuthUserInfoUrl = StringUtils.replaceEach(wechatProperties.getOAuthUserInfoUrl(), new String[]{"#OAUTH_TOKEN#", "#OPENID#"},
                new String[]{oAuthToken, openID});

        String result = OkHttpUtils.synGetString(oAuthUserInfoUrl);
        WechatUserInfo userInfo = JSON.parseObject(result, WechatUserInfo.class);
        if(StringUtils.isNotBlank(userInfo.getErrorCode())) {
            throw new WechatException(userInfo.getErrorCode(), userInfo.getErrorMessage());
        }

        //TODO store user info

        return userInfo;
    }

    public WechatUserInfo getUserInfo(String code, String state) throws Exception {

        WechatOAuthToken oAuthToken = oAuthTokenService.getWechatOAuthToken(code, state);
        oAuthTokenService.checkOAuthToken(oAuthToken.getOAuthToken(), oAuthToken.getOpenID());

        return getWechatUserInfo(oAuthToken.getOAuthToken(), oAuthToken.getOpenID());
    }
}
