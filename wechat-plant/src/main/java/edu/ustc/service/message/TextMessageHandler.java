package edu.ustc.service.message;

import edu.ustc.config.WechatException;
import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws WechatException {
        logger.info("handle text message success");
        return null;
    }
}
