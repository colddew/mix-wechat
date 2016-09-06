package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.utils.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TextMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assemble(message);

        logger.info("handle text message success");

        return replyMessage;
    }
    
    public String assemble(WechatMessage message) throws Exception {

        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName(message.getToUserName());
        replyMessage.setToUserName(message.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
        replyMessage.setMessageType(MessageType.text.name());
        replyMessage.setContent("welcome to plant home");

        return JaxbUtils.convertObjectToXml(replyMessage);
    }
}
