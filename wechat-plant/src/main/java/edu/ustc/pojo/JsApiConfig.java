package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class JsApiConfig {

    @JSONField(name = "ticket")
    private String ticket;

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

    @JSONField(name = "appId")
    private String appID;

    @JSONField(name = "timestamp")
    private String timestamp;

    @JSONField(name = "nonceStr")
    private String random;

    @JSONField(name = "signature")
    private String signature;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
