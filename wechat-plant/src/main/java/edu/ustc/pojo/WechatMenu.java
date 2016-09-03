package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WechatMenu {

    @JSONField(name = "button")
    private List<WechatSubMenu> button;

    public List<WechatSubMenu> getButton() {
        return button;
    }

    public void setButton(List<WechatSubMenu> button) {
        this.button = button;
    }
}
