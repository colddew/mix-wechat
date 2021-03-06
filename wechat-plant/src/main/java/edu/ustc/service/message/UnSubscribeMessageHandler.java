package edu.ustc.service.message;

import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UnSubscribeMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(UnSubscribeMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {
        logger.info("handle unsubscribe message success");
        return null;
    }
}
