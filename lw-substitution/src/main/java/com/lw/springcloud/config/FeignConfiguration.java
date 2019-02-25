package com.lw.springcloud.config;

import com.lw.springcloud.interceptor.BasicAuthInterceptor;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    /**
     * 定义deign日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    BasicAuthInterceptor BasicAuthInterceptor(){
        return new BasicAuthInterceptor("123");
    }

    /**
     * 配置feign 连接超时时间 读取超时时间
     * @return
     */
    @Bean
    Request.Options options(){
        return new Request.Options(5*1000,10*1000);
    }
}
