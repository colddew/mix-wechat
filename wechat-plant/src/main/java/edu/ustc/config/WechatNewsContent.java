package edu.ustc.config;

import com.alibaba.fastjson.annotation.JSONField;
import edu.ustc.pojo.WechatNewsItem;

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
