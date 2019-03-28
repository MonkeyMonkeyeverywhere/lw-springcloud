package com.lw.cloud.transactionmqtask.rabbitmqclient;

import com.lw.cloud.feignclient.TransactionMessageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TransactionMessageConfirm implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private TransactionMessageClient transactionMessageClient;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            log.info("更新发送次数和发送时间");
            transactionMessageClient.incrSendCount(Long.valueOf(correlationData.getId()),DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            log.info("消息发送确认失败：{}", cause);
        }
    }
}
