package edu.ustc.config;

public class WechatConstants {

    public static final long WECHAT_COMMON_REFRESH_PERIOD_SECONDS = 3600;

    public static final long WECHAT_ACCESS_TOKEN_EXPIRY_SECONDS = 7200;
    public static final long WECHAT_OAUTH_TOKEN_EXPIRY_SECONDS = 7200;
    public static final long WECHAT_OAUTH_REFRESH_TOKEN_EXPIRY_DAYS = 30;

    public static final String WECHAT_PLANT_TOKEN = "mix2wechat";       // for developer server check
    public static final String WECHAT_AUTHORIZE_STATE_BASE = "authorize_base";
    public static final String WECHAT_AUTHORIZE_STATE_USERINFO = "authorize_userinfo";

    public static final int AVATAR_SIZE_0 = 0;      // the same to 640
    public static final int AVATAR_SIZE_46 = 46;
    public static final int AVATAR_SIZE_64 = 64;
    public static final int AVATAR_SIZE_96 = 96;
    public static final int AVATAR_SIZE_132 = 132;

    public static final int IS_MENU_OPEN_NO = 0;
    public static final int IS_MENU_OPEN_YES = 1;

    public static final int SHOW_COVER_NO = 0;
    public static final int SHOW_COVER_YES = 1;

    public static final int SHOW_COVER_PICTURE_NO = 0;
    public static final int SHOW_COVER_PICTURE_YES = 1;

    public static final int MATERIAL_MAX_LIMIT_IMAGE_AND_NEWS = 5000;
    public static final int MATERIAL_MAX_LIMIT_OTHERS = 1000;
}
