package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatAccessToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private String expiry;

    @JSONField(name = "errcode")
    private String errorCode;

    @JSONField(name = "errmsg")
    private String errorMessage;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
