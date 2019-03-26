package com.lw.cloud.feignclient;

import com.lw.cloud.config.FeignConfig;
import com.lw.cloud.dto.TransactionMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient(value = "transaction-mq-service", path = "message",configuration = FeignConfig.class)
public interface TransactionMessageClient {

    /**
     * 发送消息
     * @param message
     * @return
     */
    @PostMapping("send")
    boolean sendMessage(TransactionMessage message);

    /**
     * 批量发送消息
     * @param messages
     * @return
     */
    @PostMapping("sends")
    boolean sendMessage(List<TransactionMessage> messages);
}
