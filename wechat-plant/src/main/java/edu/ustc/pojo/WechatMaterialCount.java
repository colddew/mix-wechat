package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by colddew on 2017/6/1.
 */
public class WechatMaterialCount {

    @JSONField(name = "voice_count")
    private Integer voiceCount;

    @JSONField(name = "video_count")
    private Integer videoCount;

    @JSONField(name = "image_count")
    private Integer imageCount;

    @JSONField(name = "news_count")
    private Integer newsCount;

    @JSONField(name = "errcode")
    private String errorCode;

    @JSONField(name = "errmsg")
    private String errorMessage;

    public Integer getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(Integer voiceCount) {
        this.voiceCount = voiceCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
