package com.lw.cloud.transactionmqtask;

import com.lw.cloud.transactionmqtask.task.ProcessMessageTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lw")
@ComponentScan(basePackages = "com.lw")
public class TransactionMqTaskApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransactionMqTaskApplication.class, args);
        try {
            ProcessMessageTask task = context.getBean(ProcessMessageTask.class);
            task.start();
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            log.error("项目启动异常", e);
        }
    }

}
