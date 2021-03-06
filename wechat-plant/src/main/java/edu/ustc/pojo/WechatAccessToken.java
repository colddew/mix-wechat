package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class WechatAccessToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private String expiry;

    @JSONField(name = "errcode")
    private String errorCode;

    @JSONField(name = "errmsg")
    private String errorMessage;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expiryStartTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expiryEndTime;

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

    public Date getExpiryStartTime() {
        return expiryStartTime;
    }

    public void setExpiryStartTime(Date expiryStartTime) {
        this.expiryStartTime = expiryStartTime;
    }

    public Date getExpiryEndTime() {
        return expiryEndTime;
    }

    public void setExpiryEndTime(Date expiryEndTime) {
        this.expiryEndTime = expiryEndTime;
    }
}
