package com.lw.cloud.annotation;

import java.lang.annotation.*;

/**
 * aop打印信息
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
    /**
     * 方法名称
     * @return
     */
    String value();
}
