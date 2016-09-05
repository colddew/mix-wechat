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
    MASSS_END_JOB_FINISH("masssendjobfinish", "群发任务结束"),
    TEMPLATES_END_JOB_FINISH("templatesendjobfinish", "模版消息发送任务结束"),
    QUALIFICATION_VERIFY_SUCCESS("qualification_verify_success", "资质认证成功获得接口权限"),
    QUALIFICATION_VERIFY_FAIL("qualification_verify_fail", "资质认证失败"),
    NAMING_VERIFY_SUCCESS("naming_verify_success", "名称认证成功获得打勾认证标识"),
    NAMING_VERIFY_FAIL("naming_verify_fail", "名称认证失败"),
    ANNUAL_RENEW("annual_renew", "年审通知"),
    VERIFY_EXPIRED("verify_expired", "认证过期失效通知");

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
