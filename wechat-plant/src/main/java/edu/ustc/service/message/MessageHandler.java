package edu.ustc.service.message;

import edu.ustc.config.MessageType;
import edu.ustc.dto.ReplyMessage;
import edu.ustc.dto.WechatMessage;
import edu.ustc.utils.JaxbUtils;

import java.time.Instant;

public interface MessageHandler<T> {

    public T handle(WechatMessage message) throws Exception;

    default String assemble(WechatMessage message) throws Exception {

        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName(message.getToUserName());
        replyMessage.setToUserName(message.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
        replyMessage.setMessageType(MessageType.text.name());
        replyMessage.setContent("暂不支持该类型的消息~~");

        return JaxbUtils.convertObjectToXml(replyMessage);
    }

    default String assembleForCustomerService(WechatMessage message) throws Exception {

        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName(message.getToUserName());
        replyMessage.setToUserName(message.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
        replyMessage.setMessageType(MessageType.transfer_customer_service.name());

        return JaxbUtils.convertObjectToXml(replyMessage);
    }
}
