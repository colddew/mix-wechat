package edu.ustc.service.message;

import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClickMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClickMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {
        logger.info("handle click message success");
        return null;
    }
}
