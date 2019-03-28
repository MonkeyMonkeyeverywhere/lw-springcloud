package com.lw.cloud.transactionmqtask.config;

import com.lw.cloud.transactionmqtask.rabbitmqclient.TransactionMessageConfirm;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue("transaction_mq");
    }

}
