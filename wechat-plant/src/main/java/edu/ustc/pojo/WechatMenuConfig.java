package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatMenuConfig {

    @JSONField(name = "is_menu_open")
    private Integer isMenuOpen;

    @JSONField(name = "selfmenu_info")
    private WechatMenu menu;

    public Integer getIsMenuOpen() {
        return isMenuOpen;
    }

    public void setIsMenuOpen(Integer isMenuOpen) {
        this.isMenuOpen = isMenuOpen;
    }

    public WechatMenu getMenu() {
        return menu;
    }

    public void setMenu(WechatMenu menu) {
        this.menu = menu;
    }
}
