package com.lw.cloud.config;

import com.lw.cloud.filter.AuthFilter;
import com.lw.cloud.filter.AuthHeaderFilter;
import com.lw.cloud.filter.ErrorFilter;
import com.lw.cloud.filter.IpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public IpFilter ipFilter(){
        return new IpFilter();
    }

    @Bean
    public ErrorFilter errorFilter(){
        return new ErrorFilter();
    }

    @Bean
    public AuthHeaderFilter authHeaderFilter(){
        return new AuthHeaderFilter();
    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
}
