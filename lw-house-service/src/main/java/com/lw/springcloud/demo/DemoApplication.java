package com.lw.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

    public static void main(String[] args) {
        // 启动时初始化配置信息
        System.setProperty("smconf.conf.package", "com.lw.springcloud.demo.config");
        SpringApplication.run(DemoApplication.class, args);
    }

}
