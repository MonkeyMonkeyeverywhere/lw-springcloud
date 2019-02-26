package com.lw.springcloud.feignclient.hystrix;

import com.lw.springcloud.feignclient.RemoteHouseClient;
import org.springframework.stereotype.Component;

@Component
public class RemoteHouseClientHystrix implements RemoteHouseClient {
    @Override
    public String hello() {
        return "that's it";
    }
}
