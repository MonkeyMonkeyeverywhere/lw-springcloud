package com.lw.springcloud.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class BasicAuthInterceptor implements RequestInterceptor {

    private String token;

    public BasicAuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("************requestTemplate************"+requestTemplate);
        if("123".equals(token)){
            System.out.println("************token验证通过！************");
            requestTemplate.header("token","123");
        }else {
            System.out.println("************token验证失败！************");
        }
    }
}
