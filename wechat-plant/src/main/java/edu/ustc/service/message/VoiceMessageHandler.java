package edu.ustc.service.message;

import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoiceMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(VoiceMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {

        String replyMessage = assembleForCustomerService(message);

        logger.info("handle voice message success");

        return replyMessage;
    }
}
