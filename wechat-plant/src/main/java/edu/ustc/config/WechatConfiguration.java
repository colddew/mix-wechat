package edu.ustc.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class WechatConfiguration {

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {

        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charsets.UTF_8);
        converter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));

        return converter;
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {

        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        messageConverters.add(fastJsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }
}
