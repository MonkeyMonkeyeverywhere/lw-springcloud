package com.lw.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class LwHouseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwHouseApiApplication.class, args);
    }

}
