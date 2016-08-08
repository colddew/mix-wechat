package edu.ustc.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import edu.ustc.config.WechatConstants;
import edu.ustc.dto.VerificationRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

@Controller
@EnableAutoConfiguration
@RequestMapping("/plant")
public class WechatPlantController {

    private static final Logger logger = LoggerFactory.getLogger(WechatPlantController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public void verify(VerificationRequest request, HttpServletResponse response) {

        try {
            checkVerificationRequest(request);

            String hashCode = encryptWithSHA1(concatVerificationInfo(request));
            if(hashCode.equalsIgnoreCase(request.getSignature())) {
                logger.info("verify request success, {}", JSON.toJSONString(request));
                output(request, response);
            } else {
                logger.info("verify request fail, {}", JSON.toJSONString(request));
            }
        } catch (Exception e) {
            logger.error("verify request error, {}" + e.getMessage());
        }
    }

    private void checkVerificationRequest(VerificationRequest request) throws Exception {
        if(null == request || StringUtils.isBlank(request.getSignature()) || StringUtils.isBlank(request.getTimestamp())
            || StringUtils.isBlank(request.getNonce()) || StringUtils.isBlank(request.getEchostr())) {
            throw new Exception("verification request parameters are bad");
        }
    }

    private String concatVerificationInfo(VerificationRequest request) {

        ArrayList<String> list = Lists.newArrayList(WechatConstants.WECHAT_PLANT_TOKEN, Strings.nullToEmpty(request.getTimestamp()), Strings.nullToEmpty(request.getNonce()));
        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        for(String str : list) {
            sb.append(str);
        }

        return sb.toString();
    }

    private String encryptWithSHA1(String verificationInfo) {
        HashFunction hashFunction = Hashing.sha1();
        return hashFunction.hashString(verificationInfo, Charset.defaultCharset()).toString();
    }

    private void output(VerificationRequest request, HttpServletResponse response) throws IOException {

        ServletOutputStream stream = response.getOutputStream();
        stream.print(request.getEchostr());

        stream.flush();
        stream.close();
    }
}
