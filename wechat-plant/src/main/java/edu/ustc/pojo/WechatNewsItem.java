package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatNewsItem {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "author")
    private String author;

    @JSONField(name = "digest")
    private String digest;

    @JSONField(name = "content")
    private String content;

    @JSONField(name = "content_source_url")
    private String contentSourceUrl;

    @JSONField(name = "thumb_media_id")
    private String thumbMediaId;

    @JSONField(name = "show_cover_pic")
    private Integer showCoverPicture;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "thumb_url")
    private String thumbUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Integer getShowCoverPicture() {
        return showCoverPicture;
    }

    public void setShowCoverPicture(Integer showCoverPicture) {
        this.showCoverPicture = showCoverPicture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
