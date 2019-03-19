package com.lw.cloud.conf;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

@CxytianDiConf(system = "lw-house-api",env = true)
public class BasicConf {

    @ConfField("api接口白名单，多个逗号分隔")
    private String apiWhiteStr = "default";

    public String getApiWhiteStr() {
        return apiWhiteStr;
    }

    public void setApiWhiteStr(String apiWhiteStr) {
        this.apiWhiteStr = apiWhiteStr;
    }

}
