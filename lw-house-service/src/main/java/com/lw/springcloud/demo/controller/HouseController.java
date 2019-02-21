package com.lw.springcloud.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class HouseController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello from lw-house-service!";
    }
}
