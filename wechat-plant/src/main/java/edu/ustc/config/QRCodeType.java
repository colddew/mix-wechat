package edu.ustc.config;

public enum QRCodeType {

    QR_SCENE("临时数字"),
    QR_LIMIT_SCENE("永久数字"),
    QR_LIMIT_STR_SCENE("永久字符串");

    private String description;

    QRCodeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
