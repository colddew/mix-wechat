package edu.ustc.config;

public class WechatException extends Exception {

    private String code;

    public WechatException(String code, String message) {
        super(message);
        this.code = code;
    }
}
