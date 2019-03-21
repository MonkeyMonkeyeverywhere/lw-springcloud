package com.lw.cloud.aspect;

import com.lw.cloud.annotation.LogInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
public class LogInfoAspect {

    private Logger logger = LoggerFactory.getLogger(LogInfoAspect.class);

    @Pointcut("@annotation(com.lw.cloud.annotation.LogInfo)")
    public void logInfo(){};

    @Before("logInfo()")
    public void recordLog(JoinPoint joinPoint){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LogInfo logInfo = method.getAnnotation(LogInfo.class);
        String value = logInfo.value();
        logger.info("{}::::::方法打印参数",value);
    }

    @AfterReturning(returning = "ret", pointcut = "logInfo()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE:::::: " + ret);
    }

}
