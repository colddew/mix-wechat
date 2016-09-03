package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WechatSubMenu {

    @JSONField(name = "sub_button")
    private List<WechatSubMenu> subButton;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "key")
    private String key;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "media_id")
    private String mediaId;

    public List<WechatSubMenu> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<WechatSubMenu> subButton) {
        this.subButton = subButton;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
