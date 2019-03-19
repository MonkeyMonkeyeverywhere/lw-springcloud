package com.lw.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LwUserServiceApplication {

    public static void main(String[] args) {
        // 启动时初始化配置信息
        System.setProperty("smconf.conf.package", "com.lw.cloud");
        SpringApplication.run(LwUserServiceApplication.class, args);
    }

}
