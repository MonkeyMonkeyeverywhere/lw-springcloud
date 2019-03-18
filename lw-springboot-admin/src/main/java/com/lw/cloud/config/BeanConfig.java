package com.lw.cloud.config;

import com.lw.cloud.DingDingNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    /* 暂时注释掉，没有钉钉token
    @Bean
    public DingDingNotifier dingDingNotifier(){
        return new DingDingNotifier();
    }*/
}
