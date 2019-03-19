package com.lw.cloud.conf;

import com.google.common.util.concurrent.RateLimiter;
import com.lw.cloud.filter.LimiterFilter;
import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

@CxytianDiConf(system = "lw-house-api",prefix = "api",env = true)
public class LimiterConf implements SmconfUpdateCallBack {

    @ConfField("api限流次数")
    private Double limitPerSecond = 100D;

    public Double getLimitPerSecond() {
        return limitPerSecond;
    }

    public void setLimitPerSecond(Double limitPerSecond) {
        this.limitPerSecond = limitPerSecond;
    }

    @Override
    public void reload(Conf conf) {
        LimiterFilter.rateLimiter = RateLimiter.create(limitPerSecond);
    }
}
