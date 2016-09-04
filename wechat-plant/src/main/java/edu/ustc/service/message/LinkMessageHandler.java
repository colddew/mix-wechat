package edu.ustc.service.message;

import edu.ustc.config.WechatException;
import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LinkMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(LinkMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws WechatException {
        logger.info("handle link message success");
        return null;
    }
}
