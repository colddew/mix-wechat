package edu.ustc.config;

public enum MenuBrief {

    CLICK_DAILY_RECOMMEND("C1001", "每日推荐", null),
    CLICK_STAR("C1002", "赞一下", null),
    VIEW_SEARCH("V1001", "搜索", "http://www.soso.com/"),
    VIEW_VIDEO("V1002", "视频", "http://v.qq.com/"),
    SCANCODE_PUSH_SCANCODE("SP1001", "扫码推事件", null),
    SCANCODE_WAITMSG_SCANCODE("SW1001", "扫码带提示", null),
    PIC_SYSPHOTO_PHOTO("PS1001", "系统拍照发图", null),
    PIC_PHOTO_OR_ALBUM_PHOTO("PP1001", "拍照或相册发图", null),
    PIC_WEIXIN_ALBUM("PW1001", "微信相册发图", null),
    LOCATION_SELECT_LOCATION("LS1001", "发送位置", null),
    MEDIA_ID_PICTURE("MI1001", "图片", null),
    VIEW_LIMITED_MESSAGE("VL1001", "图文消息", null);

    private String code;
    private String description;
    private String url;

    MenuBrief(String code, String description, String url) {
        this.code = code;
        this.description = description;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
