package com.lw.cloud.annotation;

import java.lang.annotation.*;

/**
 * 对API进行访问速度限制<br>
 * 限制的速度值在Smconf配置中通过key关联
 * @author lw
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiRateLimit {
	
	/**
	 * Smconf 配置中的key
	 * @return
	 */
	String confKey();
	
}
