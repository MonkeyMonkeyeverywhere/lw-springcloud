package com.lw.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${eureka.defaultZone}")
    private String defaultZone;

    @RequestMapping("/hello")
    public String hello(){
        return defaultZone+"hello from lw-house-service!"+serverPort;
    }
}
