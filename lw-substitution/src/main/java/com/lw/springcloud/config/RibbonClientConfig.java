package com.lw.springcloud.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(name = "lw-house-service",configuration = RibbonRuleConfig.class)
public class RibbonClientConfig {
}
