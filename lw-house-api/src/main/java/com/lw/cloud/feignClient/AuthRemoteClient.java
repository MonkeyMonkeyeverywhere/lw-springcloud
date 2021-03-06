package com.lw.cloud.feignClient;

import com.lw.cloud.base.ResponseData;
import com.lw.cloud.query.AuthQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * token认证客户端
 * @author wei.liu
 */
@FeignClient(value = "lw-auth-service", path = "/oauth")
public interface AuthRemoteClient {

    @RequestMapping("token")
    ResponseData auth(AuthQuery query);
}
