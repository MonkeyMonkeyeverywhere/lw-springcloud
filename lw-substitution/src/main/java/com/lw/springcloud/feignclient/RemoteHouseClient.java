package com.lw.springcloud.feignclient;

import com.lw.springcloud.config.FeignConfiguration;
import com.lw.springcloud.feignclient.hystrix.RemoteHouseClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "lw-house-service",path = "house" ,configuration = FeignConfiguration.class , fallback = RemoteHouseClientHystrix.class)
public interface RemoteHouseClient {

    @GetMapping("hello")
    String hello();
}
