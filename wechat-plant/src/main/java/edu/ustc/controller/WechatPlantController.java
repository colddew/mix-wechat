package edu.ustc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.ustc.dto.VerificationRequest;
import edu.ustc.dto.WechatMessage;
import edu.ustc.pojo.JsApiConfig;
import edu.ustc.pojo.WechatUserInfo;
import edu.ustc.service.WechatPlantService;
import edu.ustc.service.message.MessageDispatcher;
import edu.ustc.service.message.MessageHandler;
import edu.ustc.utils.JaxbUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Controller
@EnableAutoConfiguration
@RequestMapping("/plant")
public class WechatPlantController {

    private static final Logger logger = LoggerFactory.getLogger(WechatPlantController.class);

    @Autowired
    private WechatPlantService wechatPlantService;

    @Autowired
    private MessageDispatcher messageDispatcher;

    // ----------------------------- link to wechat -----------------------------

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public void verify(VerificationRequest request, HttpServletResponse response) {

        try {
            checkVerificationRequest(request);

            if(wechatPlantService.checkSignature(request)) {
                logger.info("verify request success, {}", JSON.toJSONString(request));
                output(request, response);
            } else {
                logger.info("verify request fail, {}", JSON.toJSONString(request));
            }
        } catch (Exception e) {
            logger.error("verify request error, {}", e.getMessage());
        }
    }

    private void checkVerificationRequest(VerificationRequest request) throws Exception {
        if(null == request || StringUtils.isBlank(request.getSignature()) || StringUtils.isBlank(request.getTimestamp())
            || StringUtils.isBlank(request.getNonce()) || StringUtils.isBlank(request.getEchostr())) {
            throw new Exception("verification request parameters are bad");
        }
    }

    private void output(VerificationRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(request.getEchostr());
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public void pushMessage(HttpServletRequest request, HttpServletResponse response) {

        try {
            String wechatMessage = CharStreams.toString(new InputStreamReader(request.getInputStream(), Charsets.UTF_8));
            logger.info("push wechat message, {}", wechatMessage);

            if(StringUtils.isNotBlank(wechatMessage)) {

                WechatMessage message = JaxbUtils.convertXmlToObject(wechatMessage, WechatMessage.class);

                MessageHandler handler = messageDispatcher.dispatch(message);
                String replyMessage = (String) handler.handle(message);

                logger.info("push wechat message success");
                output(replyMessage, response);
            } else {
                logger.info("push wechat message fail");
                output(StringUtils.EMPTY, response);
            }
        } catch (Exception e) {
            logger.error("push wechat message error, {}", e.getMessage());
            try {
                output(StringUtils.EMPTY, response);
            } catch (IOException ioe) {
                logger.error("push wechat message error, {}", ioe.getMessage());
            }
        }
    }

    private void output(String replyMessage, HttpServletResponse response) throws IOException {

        if(StringUtils.isNotBlank(replyMessage)) {

            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.write(replyMessage);
            writer.flush();
            writer.close();
        }
    }

    @RequestMapping(value = "/jsApiConfig", method = RequestMethod.GET)
    @ResponseBody
    public JsApiConfig getJsApiConfig(String url) {
        try {
            JsApiConfig jsApiConfig = wechatPlantService.getJsApiConfig(url);
            logger.info("get js api config success, {}", JSON.toJSONString(jsApiConfig));
            return jsApiConfig;
        } catch (Exception e) {
            logger.error("get js api config error, {}", e.getMessage());
            return null;
        }
    }

    // ----------------------------- get user info -----------------------------

    @RequestMapping(value = "/wechatUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public WechatUserInfo getWechatUserInfo(String oAuthToken, String openID) {
        try {
            WechatUserInfo userInfo = wechatPlantService.getWechatUserInfo(oAuthToken, openID);
            logger.info("get wechat user info success, {}", JSON.toJSONString(userInfo));
            return userInfo;
        } catch (Exception e) {
            logger.error("get wechat user info error, {}", e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public WechatUserInfo getUserInfo(String code, String state) {
        try {
            WechatUserInfo userInfo = wechatPlantService.getUserInfo(code, state);
            logger.info("get user info success, {}", JSON.toJSONString(userInfo));
            return userInfo;
        } catch (Exception e) {
            logger.error("get user info error, {}", e.getMessage());
            return null;
        }
    }

    // ----------------------------- manipulate menu -----------------------------

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ResponseBody
    public String createMenu() {
        try {
            return wechatPlantService.createMenu();
        } catch (Exception e) {
            logger.error("create menu error, {}", e.getMessage());
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getMenu() {
        try {
            return wechatPlantService.getMenu();
        } catch (Exception e) {
            logger.error("get menu error, {}", e.getMessage());
            return null;
        }
    }
}
