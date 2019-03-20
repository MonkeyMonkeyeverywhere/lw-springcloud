package com.lw.cloud.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.lw.cloud.base.ResponseCode;
import com.lw.cloud.base.ResponseData;
import com.lw.cloud.util.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * 限流过滤器
 *
 * @author wei.liu
 */
public class LimiterFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(LimiterFilter.class);

    public volatile static RateLimiter rateLimiter = RateLimiter.create(Double.parseDouble(System.getProperty("api.limitPerSecond")));

    @Autowired
    @Qualifier("longRedisTemplate")
    private RedisTemplate<String,Long> redisTemplate;

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
        logger.info("api集群限流" );
        RequestContext ctx = RequestContext.getCurrentContext();
        // 保证服务器时间一直
        long currentSecond = System.currentTimeMillis() / 1000;
        String key = "lw-api-rate-limit-" + currentSecond;
        try {
            if(!redisTemplate.hasKey(key)){
                redisTemplate.opsForValue().set(key,0L );
            }
            double clusterLimitPerSecond = Double.parseDouble(System.getProperty("api.clusterLimitPerSecond"));
            if(redisTemplate.opsForValue().increment(key,1L ) > clusterLimitPerSecond){
                logger.info("当前负载太高，请稍后重试" );
                ctx.setSendZuulResponse(false);
                ctx.set("isSuccess",false );
                ResponseData data = ResponseData.fail("当前负载太高，请稍后重试", ResponseCode.LIMIT_ERROR_CODE.getCode());
                ctx.setResponseBody(JsonUtils.toJson(data));
                ctx.getResponse().setContentType("application/json; charset=utf-8");
                return null;
            }
        }catch (Exception e) {
            logger.info("集群限流有误，切换为单体限流！" );
            rateLimiter.acquire();
        }
        return null;
    }
}
