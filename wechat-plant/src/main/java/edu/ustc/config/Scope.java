package edu.ustc.config;

public enum Scope {

    BASE("snsapi_base", "获取进入页面的用户的openid, 静默授权并自动跳转到回调页"),
    USER_INFO("snsapi_userinfo", "获取用户的基本信息, 一般需要用户手动同意");

    private String code;
    private String description;

    Scope(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
