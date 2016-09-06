package edu.ustc.config;

public enum MenuBrief {

    CLICK_DAILY_RECOMMEND("T_C1001", "每日推荐", null),
    CLICK_STAR("T_C1002", "赞一下", null),
    VIEW_SEARCH("T_V1001", "搜索", "http://www.soso.com/"),
    VIEW_VIDEO("T_V1002", "视频", "http://v.qq.com/"),
    SCANCODE_PUSH_SCANCODE("T_SP1001", "扫码推事件", null),
    SCANCODE_WAITMSG_SCANCODE("T_SW1001", "扫码带提示", null),
    PIC_SYSPHOTO_PHOTO("T_PS1001", "系统拍照发图", null),
    PIC_PHOTO_OR_ALBUM_PHOTO("T_PP1001", "拍照或相册发图", null),
    PIC_WEIXIN_ALBUM("T_PW1001", "微信相册发图", null),
    LOCATION_SELECT_LOCATION("T_LS1001", "发送位置", null),
    MEDIA_ID_PICTURE("T_MI1001", "图片", null),
    VIEW_LIMITED_MESSAGE("T_VL1001", "图文消息", null),

    CLICK_PLANT_EXPERIENCE("P_C1001", "绿植心经", null),
    CLICK_PLANT_SHARE("P_C1002", "我有绿植", null),
    CLICK_PLANT_MORE("P_C1003", "更多", null),
    VIEW_PLANT_GARDEN("P_T1001", "绿植花园", "https://2c7978f5.ngrok.io/html/garden.html"),
    VIEW_PLANT_BALCONY("P_T1002", "绿色阳台", "https://2c7978f5.ngrok.io/html/balcony.html"),
    VIEW_PLANT_LINK("P_T1003", "绿色连萌", "https://2c7978f5.ngrok.io/html/link.html"),
    VIEW_PLANT_ABOUT("P_T1004", "关于我们", "https://2c7978f5.ngrok.io/html/about.html");

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
