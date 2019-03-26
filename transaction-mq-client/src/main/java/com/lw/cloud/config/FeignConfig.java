package com.lw.cloud.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    Request.Options options(){
        return new Request.Options(5*1000,10*1000);
    }
}
