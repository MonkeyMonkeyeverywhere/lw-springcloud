package com.lw.springcloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Map;

public class BasicAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", System.getProperty("lw.auth.token"));
    }
}
