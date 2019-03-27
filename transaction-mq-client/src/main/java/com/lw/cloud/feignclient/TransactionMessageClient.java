package com.lw.cloud.feignclient;

import com.lw.cloud.config.FeignConfig;
import com.lw.cloud.dto.TransactionMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 累加发送次数
     * @param messageId 消息ID
     * @param sendDate  发送时间（task服务中的时间，防止服务器之间时间不同步问题）
     * @return
     */
    @PostMapping("/incrSendCount")
    boolean incrSendCount(@RequestParam("messageId") Long messageId, @RequestParam("sendDate") String sendDate);

    /**
     * 确认消息死亡
     * @param messageId 消息ID
     * @return
     */
    @PostMapping("/confirm/die")
    boolean confirmDieMessage(@RequestParam("messageId") Long messageId);

    /**
     * 确认消息被消费
     * @param customerSystem  消费的系统
     * @param messageId	消息ID
     * @return
     */
    @PostMapping("/confirm/customer")
    boolean confirmCustomerMessage(@RequestParam("customerSystem") String customerSystem,
                                   @RequestParam("messageId") Long messageId);

    /**
     * 查询最早没有被消费的消息
     * @param limit	查询条数
     * @return
     */
    @GetMapping("/wating")
    List<TransactionMessage> findByWatingMessage(@RequestParam("limit") int limit);

    /**
     * 重新发送当前已死亡的消息
     * @return
     */
    @PostMapping("/send/retry")
    boolean retrySendDieMessage();
}
