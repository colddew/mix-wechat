package edu.ustc.service.message;

import edu.ustc.dto.WechatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScanMessageHandler implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ScanMessageHandler.class);

    @Override
    public Object handle(WechatMessage message) throws Exception {
        logger.info("handle scan message success");
        return null;
    }
}
