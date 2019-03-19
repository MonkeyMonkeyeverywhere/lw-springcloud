package com.lw.cloud.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 限流过滤器
 *
 * @author wei.liu
 */
public class LimiterFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(LimiterFilter.class);

    public volatile static RateLimiter rateLimiter = RateLimiter.create(Double.parseDouble(System.getProperty("api.limitPerSecond")));

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        rateLimiter.acquire();
        return null;
    }
}
