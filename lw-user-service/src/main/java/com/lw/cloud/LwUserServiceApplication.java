package com.lw.cloud;

import com.lw.cloud.listener.InitApiLimitRateListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lw")
@EnableCaching
public class LwUserServiceApplication {

    public static ConfigurableApplicationContext context = null;

    public static void main(String[] args) {
        // 启动时初始化配置信息
        System.setProperty("smconf.conf.package", "com.lw.cloud");
        SpringApplication application = new SpringApplication(LwUserServiceApplication.class);
        application.addListeners(new InitApiLimitRateListener("com.lw.cloud.controller"));
        context = application.run(args);
    }

}
