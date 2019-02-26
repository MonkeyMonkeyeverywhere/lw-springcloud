package com.lw.springcloud.config;

import com.lw.springcloud.rule.MyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonRuleConfig {

//    @Bean
    public MyRule rule(){
        return new MyRule();
    }

}
