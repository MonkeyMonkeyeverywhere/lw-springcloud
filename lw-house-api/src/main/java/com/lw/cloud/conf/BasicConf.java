package com.lw.cloud.conf;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;

@CxytianDiConf(system = "lw-house-api")
public class BasicConf {

    @ConfField("api接口白名单，多个逗号分隔")
    private String apiWhiteStr;

    public String getApiWhiteStr() {
        return apiWhiteStr;
    }

    public void setApiWhiteStr(String apiWhiteStr) {
        this.apiWhiteStr = apiWhiteStr;
    }
}
