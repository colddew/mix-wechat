package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.Article;
import edu.ustc.dto.Articles;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.utils.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClickMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assemble(message);

        logger.info("handle click message success");

        return replyMessage;
    }

    public String assemble(WechatMessage message) throws Exception {

        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName(message.getToUserName());
        replyMessage.setToUserName(message.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
        replyMessage.setMessageType(MessageType.news.name());
        replyMessage.setArticleCount(3);

        Article article1 = new Article();
        article1.setTitle("何为绿色植物");
        article1.setDescription("绿色植物养植经验分享1");
        article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/ia0pU931licEwApa9SegGOgPzmyibnydibUnVqEYA1MIFFrhOaYzFafy4ibFQ8Jps9EcC2wg6AA4Hr9PdctKDPa682w/0?wx_fmt=png");
        article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzIyNTM0Mzk3Mw==&mid=100000009&idx=1&sn=213daaaa5174c5cf417353c6a2ac0532#rd");

        Article article2 = new Article();
        article2.setTitle("生长习性");
        article2.setDescription("绿色植物养植经验分享2");
        article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/ia0pU931licEwApa9SegGOgPzmyibnydibUnsOXIKZpp2vdulvxTg0UbvzR8g4jx4AicQae2RWZ17Um7jhN6yKf1MqQ/0?wx_fmt=png");
        article2.setUrl("http://mp.weixin.qq.com/s?__biz=MzIyNTM0Mzk3Mw==&mid=100000013&idx=1&sn=b1951d2af161ef4ce97029753b4495ec#rd");

        Article article3 = new Article();
        article3.setTitle("最老的种子植物");
        article3.setDescription("绿色植物养植经验分享3");
        article3.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/ia0pU931licEwApa9SegGOgPzmyibnydibUngScdtR8ZtzIlfZKibO7deDlTqpQxwsP5DgIiadtqxKbDKI8Abv9m1ALw/0?wx_fmt=png");
        article3.setUrl("http://mp.weixin.qq.com/s?__biz=MzIyNTM0Mzk3Mw==&mid=100000016&idx=1&sn=d32ccaf15dc2e4e3a2569ecad148044f#rd");

        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);

        Articles articles = new Articles();
        articles.setArticles(articleList);
        replyMessage.setArticles(articles);

        return JaxbUtils.convertObjectToXml(replyMessage);
    }
}
