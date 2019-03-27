package com.lw.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TransactionMqServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMqServiceApplication.class, args);
    }

}
