package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.Article;
import edu.ustc.dto.Articles;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.pojo.WechatNewsItem;
import edu.ustc.service.WechatPlantService;
import edu.ustc.utils.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClickMessageHandler.class);

    @Autowired
    private WechatPlantService wechatPlantService;

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assemble(message);

        logger.info("handle click message success");

        return replyMessage;
    }

    public String assemble(WechatMessage message) throws Exception {

        List<WechatNewsItem> wechatNewsItemList = wechatPlantService.getWechatNewsItemList();
        if(!CollectionUtils.isEmpty(wechatNewsItemList)) {

            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setFromUserName(message.getToUserName());
            replyMessage.setToUserName(message.getFromUserName());
            replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
            replyMessage.setMessageType(MessageType.news.name());
            replyMessage.setArticleCount(wechatNewsItemList.size());
            replyMessage.setArticles(assembleArticles(wechatNewsItemList));

            return JaxbUtils.convertObjectToXml(replyMessage);
        }

        return null;
    }

    private Articles assembleArticles(List<WechatNewsItem> wechatNewsItemList) {

        List<Article> articleList = new ArrayList<>();
        for(WechatNewsItem wechatNewsItem : wechatNewsItemList) {

            Article article = new Article();
            article.setTitle(wechatNewsItem.getTitle());
            article.setDescription(wechatNewsItem.getDigest());
            article.setPicUrl(wechatNewsItem.getThumbUrl());
            article.setUrl(wechatNewsItem.getUrl());

            articleList.add(article);
        }

        Articles articles = new Articles();
        articles.setArticles(articleList);

        return articles;
    }
}
