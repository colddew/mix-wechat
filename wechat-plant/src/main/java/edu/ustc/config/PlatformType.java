package edu.ustc.config;

public enum PlatformType {

    IOS(1),
    Android(2),
    Others(3);

    private Integer code;

    PlatformType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
