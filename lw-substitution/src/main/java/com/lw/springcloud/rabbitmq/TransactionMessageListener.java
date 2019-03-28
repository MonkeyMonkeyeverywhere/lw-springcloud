package com.lw.springcloud.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.lw.cloud.dto.TransactionMessage;
import com.lw.cloud.feignclient.TransactionMessageClient;
import com.lw.springcloud.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "transaction_mq")
@Slf4j
public class TransactionMessageListener {

    @Autowired
    private TransactionMessageClient transactionMessageClient;

    @RabbitHandler
    public void process(String message){
        log.info("可靠消息服务消费信息：{}", message);
        MessageDto messageDto = JSONUtil.toBean(message, MessageDto.class);
        log.info("修改房源信息============>{}", messageDto.getMessage());
        boolean res = transactionMessageClient.confirmCustomerMessage("lw-substitution",messageDto.getMessageId());
        if(!res){
            throw new RuntimeException("可靠消息服务消费信息出错！");
        }
    }

}
