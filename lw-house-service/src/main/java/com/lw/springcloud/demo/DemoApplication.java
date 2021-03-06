package com.lw.springcloud.demo;

import com.lw.cloud.listener.InitApiLimitRateListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.lw")
@EnableFeignClients(basePackages = "com.lw")
public class DemoApplication {

    public static ConfigurableApplicationContext context = null;

    public static void main(String[] args) {
        // 启动时初始化配置信息
        System.setProperty("smconf.conf.package", "com.lw.springcloud.demo.config");
        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.addListeners(new InitApiLimitRateListener("com.lw.springcloud.demo.controller"));
        context = application.run(args);
    }

}
