package com.lw.cloud.aspect;

import com.lw.cloud.annotation.ApiRateLimit;
import com.lw.cloud.util.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiLimitAspect {

    private Logger logger = LoggerFactory.getLogger(ApiLimitAspect.class);

    public static Map<String,Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    @Around("execution(* com.lw.*.*.controller.*.*(..))")
    public Object aroud(ProceedingJoinPoint joinPoint){
        Object result;
        Semaphore semaphore;
        Class<?> clazz = joinPoint.getTarget().getClass();
        String key = getRateLimitKey(clazz, joinPoint.getSignature().getName());
        if(StringUtils.isEmpty(key)){
            semaphore = semaphoreMap.get("open.api.defaultLimit");
        }else {
            semaphore = semaphoreMap.get(key);
        }
        try {
            semaphore.acquire();
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
        return result;
    }

    private String getRateLimitKey(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if(method.getName().equals(methodName)){
                if (method.isAnnotationPresent(ApiRateLimit.class)) {
                    String key = method.getAnnotation(ApiRateLimit.class).confKey();
                    return key;
                }
            }
        }
        return null;
    }

}
