package com.lw.cloud.task;

import com.lw.cloud.base.ResponseData;
import com.lw.cloud.feignClient.AuthRemoteClient;
import com.lw.cloud.query.AuthQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class TokenScheduledTask {

    private static Logger logger = LoggerFactory.getLogger(TokenScheduledTask.class);

    /**一分钟*/
    private final static long REFRESH_TIME = 1000*60;

    @Autowired
    AuthRemoteClient authRemoteClient;

    @Scheduled(fixedDelay = REFRESH_TIME)
    public void reloadApiToken(){
        System.out.println("==========刷新api token=========");
        String token = getToken();
        while (StringUtils.isEmpty(token)){
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("刷新api token出错！{}",e.getMessage());
            }
            token = getToken();
        }
        logger.info("token刷新：{}",token );
        System.setProperty("lw.auth.token", token);
    }

    public String getToken() {
        AuthQuery query = new AuthQuery();
        //临时数据，实际为分配的key
        query.setAccessKey("1");
        query.setSecretKey("1");
        ResponseData response = authRemoteClient.auth(query);
        return response.getData() == null ? "" : response.getData().toString();
    }

}
