package edu.ustc.config;

public enum EventType {

    CLICK("click", "点击推事件"),
    VIEW("view", "跳转URL"),
    SCANCODE_PUSH("scancode_push", "扫码推事件"),
    SCANCODE_WAITMSG("scancode_waitmsg", "扫码带提示"),
    PIC_SYSPHOTO("pic_sysphoto", "系统拍照发图推事件"),
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album", "系统拍照或微信相册发图推事件"),
    PIC_WEIXIN("pic_weixin", "微信相册发图推事件"),
    LOCATION_SELECT("location_select", "地理位置选择"),
    MEDIA_ID("media_id", "永久素材下发"),
    VIEW_LIMITED("view_limited", "跳转永久素材URL"),
    SUBSCRIBE("subscribe", "关注"),
    UNSUBSCRIBE("unsubscribe", "取消关注"),
    SCAN("scan", "扫描带参数二维码"),
    LOCATION("location", "上报地理位置"),
    MASSSENDJOBFINISH("masssendjobfinish", "群发任务结束"),
    TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH", "模版消息发送任务结束");

    private String code;
    private String description;

    EventType(String code, String description) {
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
