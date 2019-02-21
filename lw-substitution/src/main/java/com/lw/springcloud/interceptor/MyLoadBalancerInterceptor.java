package com.lw.springcloud.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class MyLoadBalancerInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final URI originalUri = request.getURI();
        System.out.println("进入自定义的拦截器中："+originalUri);
        return execution.execute(request,body );
    }
}
