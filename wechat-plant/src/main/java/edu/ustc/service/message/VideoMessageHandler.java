package edu.ustc.service.message;

import edu.ustc.config.WechatException;
import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(VideoMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws WechatException {
        logger.info("handle video message success");
        return null;
    }
}
