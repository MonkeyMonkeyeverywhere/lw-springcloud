package com.lw.cloud;

import com.lw.cloud.util.GuavaCacheUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LwCommonApplication {

    public static void main(String[] args) {
//        SpringApplication.run(LwCommonApplication.class, args);
        GuavaCacheUtil.put("key1","value1");
        GuavaCacheUtil.put("key2","value2");
        GuavaCacheUtil.put("key3","value3");
        System.out.println("第一个值：" + GuavaCacheUtil.get("key1"));
        System.out.println("第二个值：" + GuavaCacheUtil.get("key2"));
        System.out.println("第三个值：" + GuavaCacheUtil.get("key3"));
    }

}
