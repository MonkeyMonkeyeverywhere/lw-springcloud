package com.lw.cloud.transactionmqtask.rabbitmqclient;

import com.lw.cloud.util.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqClient {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String queue,String message){
        rabbitTemplate.convertAndSend(queue,message);
    }

}
