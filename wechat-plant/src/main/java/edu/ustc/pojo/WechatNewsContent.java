package edu.ustc.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WechatNewsContent {

    @JSONField(name = "news_item")
    private List<WechatNewsItem> newsItems;

    public List<WechatNewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<WechatNewsItem> newsItems) {
        this.newsItems = newsItems;
    }
}
