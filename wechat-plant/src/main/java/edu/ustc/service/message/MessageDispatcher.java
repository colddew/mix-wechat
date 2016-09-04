package edu.ustc.service.message;

import edu.ustc.config.ErrorCode;
import edu.ustc.config.MenuType;
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
    private LocationMessageHandler locationMessageHandler;

    @Resource
    private TextMessageHandler textMessageHandler;

    @Resource
    private VoiceMessageHandler voiceMessageHandler;

    @Resource
    private VideoMessageHandler videoMessageHandler;

    @Resource
    private LinkMessageHandler linkMessageHandler;

    public MessageHandler dispatch(WechatMessage message) throws WechatException {

        if(MessageType.event.name().equals(message.getMessageType()) && MenuType.CLICK.getCode().equalsIgnoreCase(message.getEvent())) {
            return clickMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.VIEW.getCode().equalsIgnoreCase(message.getEvent())) {
            return viewMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.SCANCODE_PUSH.getCode().equalsIgnoreCase(message.getEvent())) {
            return scanCodeMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.SCANCODE_WAITMSG.getCode().equalsIgnoreCase(message.getEvent())) {
            return scanCodeMessageHandler;
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.PIC_SYSPHOTO.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate photo md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.PIC_PHOTO_OR_ALBUM.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate photo or album md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.PIC_WEIXIN.getCode().equalsIgnoreCase(message.getEvent())) {
            return pictureMessageHandler;       // validate album md5 checksum
        } else if(MessageType.image.name().equals(message.getMessageType())) {
            return pictureMessageHandler;       // for md5 checksum
        } else if(MessageType.event.name().equals(message.getMessageType()) && MenuType.LOCATION_SELECT.getCode().equalsIgnoreCase(message.getEvent())) {
            return locationMessageHandler;      // fetch picture url
        } else if(MessageType.location.name().equals(message.getMessageType())) {
            return locationMessageHandler;      // latitude and longitude are more accurate
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
        } else {
            throw new WechatException(ErrorCode.UNSUPPORTED_MESSAGE_TYPE_OR_EVENT.name(), ErrorCode.UNSUPPORTED_MESSAGE_TYPE_OR_EVENT.getDescription());
        }
    }
}
