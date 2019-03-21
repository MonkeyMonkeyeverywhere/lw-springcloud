package com.lw.cloud.conf;

import com.lw.cloud.aspect.ApiLimitAspect;
import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

import java.util.concurrent.Semaphore;


/**
 * 接口限流配置
 * @author lw
 *
 */
@CxytianDiConf(system = "lw-house-service", env = true, prefix = "open.api")
public class RestAPIRateLimitConf implements SmconfUpdateCallBack {
	@ConfField("默认的接口并发限制")
	private int defaultLimit = 10;

	public int getDefaultLimit() {
		return defaultLimit;
	}

	public void setDefaultLimit(int defaultLimit) {
		this.defaultLimit = defaultLimit;
	}

	@Override
	public void reload(Conf conf) {
		ApiLimitAspect.semaphoreMap.put("open.api." + conf.getKey(), new Semaphore(Integer.parseInt(conf.getValue().toString())));
	}

}