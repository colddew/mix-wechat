package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class WechatNewsInfo {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "digest")
    private String digest;

    @JSONField(name = "author")
    private String author;

    @JSONField(name = "show_cover")
    private Integer showCover;

    @JSONField(name = "cover_url")
    private String coverUrl;

    @JSONField(name = "content_url")
    private String contentUrl;

    @JSONField(name = "source_url")
    private String sourceUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getShowCover() {
        return showCover;
    }

    public void setShowCover(Integer showCover) {
        this.showCover = showCover;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
