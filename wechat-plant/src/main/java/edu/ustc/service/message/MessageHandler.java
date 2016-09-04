package edu.ustc.service.message;

import edu.ustc.config.WechatException;
import edu.ustc.dto.WechatMessage;

public interface MessageHandler<T> {

    public T handle(WechatMessage message) throws WechatException;
}
