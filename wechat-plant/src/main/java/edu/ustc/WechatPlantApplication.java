package edu.ustc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WechatPlantApplication {
	public static void main(String[] args) throws Exception {
		new SpringApplication(WechatPlantApplication.class).run(args);
    }
}