package com.packtpub.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public BasicAuthRequestInterceptor requestInterceptor(){
        return new BasicAuthRequestInterceptor("user1", "somethingAboutYou2");
    }
}