package com.lw.springcloud.controller;

import com.lw.springcloud.feignclient.RemoteHouseClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("substitution")
public class SubstitutionController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RemoteHouseClient remoteHouseClient;

    @GetMapping("subHello")
//    @HystrixCommand(fallbackMethod = "defaltSubstitution")
//    @HystrixCollapser()
    public String substitution(){
//        return "获取订阅："+restTemplate.getForObject("http://lw-house-service/house/hello",String.class );
        return "获取订阅："+remoteHouseClient.hello();
    }

    @GetMapping("metadatas")
    public Object metadatas(){
        List<InstanceInfo> instancesByVipAddress = eurekaClient.getInstancesByVipAddress("lw-substitution", false);
        return instancesByVipAddress;
    }

    @GetMapping("choose")
    public Object chooseUrl(){
        ServiceInstance instance = loadBalancer.choose("lw-house-service");
        return instance;
    }


    /*****hystrix fallback****/

    public String defaltSubstitution(){
        return "默认返回结果！";
    }

}
