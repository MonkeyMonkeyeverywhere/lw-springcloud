package com.lw.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("substitution")
public class SubstitutionController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("subHello")
    public String substitution(){
        return "获取订阅："+restTemplate.getForObject("http://lw-house-service/house/hello",String.class );
    }

}
