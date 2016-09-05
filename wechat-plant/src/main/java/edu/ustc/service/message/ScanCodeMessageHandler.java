package edu.ustc.service.message;

import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScanCodeMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ScanCodeMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {
        logger.info("handle scancode message success");
        return null;
    }
}
