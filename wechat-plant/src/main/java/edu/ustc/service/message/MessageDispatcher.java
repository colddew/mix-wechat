package edu.ustc.service.message;

import edu.ustc.config.LocalErrorCode;
import edu.ustc.config.EventType;
import edu.ustc.config.MessageType;
import edu.ustc.config.WechatException;
import edu.ustc.dto.WechatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageDispatcher {

    @Resource
    private MessageHandler clickMessageHandler;

    @Resource
    private MessageHandler viewMessageHandler;

    @Resource
    private ScanCodeMessageHandler scanCodeMessageHandler;

    @Resource
    private PictureMessageHandler pictureMessageHandler;

    @Resource
    private LocationSelectMessageHandler locationSelectMessageHandler;

    @Resource
    private TextMessageHandler textMessageHandler;

    @Resource
    private VoiceMessageHandler voiceMessageHandler;

    @Resource
    private VideoMessageHandler videoMessageHandler;

    @Resource
    private LinkMessageHandler linkMessageHandler;

    @Resource
    private SubscribeMessageHandler subscribeMessageHandler;

    @Resource
    private UnSubscribeMessageHandler unSubscribeMessageHandler;

    @Resource
    private ScanMessageHandler scanMessageHandler;

    @Resource
    private LocationMessageHandler locationMessageHandler;

    public MessageHandler dispatch(WechatMessage message) throws Exception {

        if(MessageType.event.name().equals(message.getMessageType()) && EventType.CLICK.getCode().equalsIgnoreCase(message.getEvent())) {
            return clickMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.VIEW.getCode().equalsIgnoreCase(message.getEvent())) {
            return viewMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.SCANCODE_PUSH.getCode().equalsIgnoreCase(message.getEvent())) {
            return scanCodeMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.SCANCODE_WAITMSG.getCode().equalsIgnoreCase(message.getEvent())) {
            return scanCodeMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.PIC_SYSPHOTO.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate photo md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.PIC_PHOTO_OR_ALBUM.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate photo or album md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.PIC_WEIXIN.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate album md5 checksum
        } else if(MessageType.image.name().equals(message.getMessageType())) {
            return pictureMessageHandler;       // for md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.LOCATION_SELECT.getCode().equalsIgnoreCase(message.getEvent())) {
            return locationSelectMessageHandler;      // fetch picture url
        } else if(MessageType.location.name().equals(message.getMessageType())) {
            return locationSelectMessageHandler;      // latitude and longitude are more accurate
        } else if(MessageType.text.name().equals(message.getMessageType())) {
            return textMessageHandler;
        } else if(MessageType.voice.name().equals(message.getMessageType())) {
            return voiceMessageHandler;
        } else if(MessageType.video.name().equals(message.getMessageType())) {
            return videoMessageHandler;
        } else if(MessageType.shortvideo.name().equals(message.getMessageType())) {
            return videoMessageHandler;
        } else if(MessageType.link.name().equals(message.getMessageType())) {
            return linkMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.SUBSCRIBE.getCode().equalsIgnoreCase(message.getEvent())) {
            return subscribeMessageHandler;     // need handle scan event if there is non-null ticket sent by unsubscribe user
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.UNSUBSCRIBE.getCode().equalsIgnoreCase(message.getEvent())) {
            return unSubscribeMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.SCAN.getCode().equalsIgnoreCase(message.getEvent())) {
            return scanMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && EventType.LOCATION.getCode().equalsIgnoreCase(message.getEvent())) {
            return locationMessageHandler;
        } else {
            throw new WechatException(LocalErrorCode.UNSUPPORTED_MESSAGE_TYPE_OR_EVENT.name(), LocalErrorCode.UNSUPPORTED_MESSAGE_TYPE_OR_EVENT.getDescription());
        }
    }
}
