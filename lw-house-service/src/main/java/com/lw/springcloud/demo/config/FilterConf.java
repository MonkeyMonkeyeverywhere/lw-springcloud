package com.lw.springcloud.demo.config;

import com.lw.cloud.HttpBasicAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConf {

    @Bean
    public HttpBasicAuthFilter httpBasicAuthFilter(){
        return new HttpBasicAuthFilter();
    }
}
