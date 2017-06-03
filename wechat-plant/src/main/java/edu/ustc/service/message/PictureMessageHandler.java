package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.Image;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.utils.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PictureMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(PictureMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assembleForCustomerService(message);

        logger.info("handle picture message success");

        return replyMessage;
    }

    public String assemble(WechatMessage message) throws Exception {

        if(MessageType.image.name().equals(message.getMessageType())) {

            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setFromUserName(message.getToUserName());
            replyMessage.setToUserName(message.getFromUserName());
            replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
            replyMessage.setMessageType(MessageType.image.name());

            Image image = new Image();
            image.setMediaId(message.getMediaId());
            replyMessage.setImage(image);

            return JaxbUtils.convertObjectToXml(replyMessage);
        }

        return null;
    }
}
