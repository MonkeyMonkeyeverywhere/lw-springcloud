package com.lw.cloud.config;

import com.lw.cloud.filter.IpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public IpFilter ipFilter(){
        return new IpFilter();
    }
}
