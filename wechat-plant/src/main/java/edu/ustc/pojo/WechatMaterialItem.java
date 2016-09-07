package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatMaterialItem {

    @JSONField(name = "media_id")
    private String mediaId;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "update_time")
    private String updateTime;

    @JSONField(name = "content")
    private WechatNewsContent content;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public WechatNewsContent getContent() {
        return content;
    }

    public void setContent(WechatNewsContent content) {
        this.content = content;
    }
}
