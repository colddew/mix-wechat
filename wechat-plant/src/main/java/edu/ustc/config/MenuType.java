package edu.ustc.config;

public enum MenuType {

    CLICK("click", "点击推事件按钮"),
    VIEW("view", "跳转URL按钮"),
    SCANCODE_PUSH("scancode_push", "扫码推事件按钮"),
    SCANCODE_WAITMSG("scancode_waitmsg", "扫码带提示按钮"),
    PIC_SYSPHOTO("pic_sysphoto", "系统拍照发图推事件按钮"),
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album", "系统拍照或微信相册发图推事件按钮"),
    PIC_WEIXIN("pic_weixin", "微信相册发图推事件按钮"),
    LOCATION_SELECT("location_select", "地理位置选择器按钮"),
    MEDIA_ID("media_id", "永久素材下发按钮"),
    VIEW_LIMITED("view_limited", "跳转永久素材URL按钮");

    private String code;
    private String description;

    MenuType(String code, String description) {
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
