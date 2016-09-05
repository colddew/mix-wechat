package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.utils.JaxbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assemble();

        logger.info("handle text message success");

        return replyMessage;
    }
    
    public String assemble() throws Exception {

        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName("gh_a90e17a6ef8e");
        replyMessage.setToUserName("o5dc-wADXQ2-KiFWxOJONAnZshgo");
        replyMessage.setCreateTime("1472979572");
        replyMessage.setMessageType(MessageType.text.name());
        replyMessage.setContent("welcome to plant home...");

        return JaxbUtils.convertObjectToXml(replyMessage);
    }
}
