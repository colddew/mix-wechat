package edu.ustc.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/wechat/plant")
public class WechatPlantController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
