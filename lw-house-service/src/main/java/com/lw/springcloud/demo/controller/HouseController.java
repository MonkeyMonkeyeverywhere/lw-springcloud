package com.lw.springcloud.demo.controller;

import com.lw.cloud.annotation.ApiRateLimit;
import com.lw.springcloud.demo.entity.HouseInfo;
import com.lw.springcloud.demo.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class HouseController {

    private Logger logger = LoggerFactory.getLogger(HouseController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private HouseService houseService;

    @Value("${eureka.defaultZone}")
    private String defaultZone;

    @ApiRateLimit(confKey = "open.api.houseHello")
    @RequestMapping("/hello")
    public String hello(){
        logger.info("request hello");
        return defaultZone+"hello from lw-house-service!"+serverPort;
    }

    @RequestMapping("/update")
    public String update(){
        logger.info("request update");
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setHouseId(1L);
        houseInfo.setName("房源一");
        return houseService.update(houseInfo)?"success":"fail";
    }
}
