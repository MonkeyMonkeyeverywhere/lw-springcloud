package com.lw.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
public class LwHouseApiApplication {

    public static void main(String[] args) {
        System.setProperty("smconf.conf.package", "com.lw.cloud");
        SpringApplication.run(LwHouseApiApplication.class, args);
    }

}
