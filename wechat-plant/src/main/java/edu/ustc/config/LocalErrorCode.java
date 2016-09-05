package edu.ustc.config;

public enum LocalErrorCode {

    UNSUPPORTED_MESSAGE_TYPE_OR_EVENT("不支持该消息类型或事件");

    private String description;

    LocalErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
