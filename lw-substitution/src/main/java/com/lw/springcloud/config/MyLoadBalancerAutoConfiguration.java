package com.lw.springcloud.config;

import com.google.common.collect.Lists;
import com.lw.springcloud.anotation.MyLoadBalanced;
import com.lw.springcloud.interceptor.MyLoadBalancerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class MyLoadBalancerAutoConfiguration {

    @MyLoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Autowired
    private MyLoadBalancerInterceptor interceptor;

    @Bean
    public SmartInitializingSingleton myLoadBalancedResttemplateInitializer(){
        return () -> {
            for(RestTemplate restTemplate : restTemplates){
                ArrayList<ClientHttpRequestInterceptor> list = Lists.newArrayList(restTemplate.getInterceptors());
//                list.add(interceptor);  //加上会报错 java.net.UnknownHostException: lw-house-service
                restTemplate.setInterceptors(list);
            }
        };
    }
}
