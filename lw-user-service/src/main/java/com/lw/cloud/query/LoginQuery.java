package com.lw.cloud.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录参数",description = "登录查询参数")
public class LoginQuery {

    @ApiModelProperty(value = "企业编号",required = true)
    private Long eid;

    @ApiModelProperty(value = "用户编号",required = true)
    private String uid;

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
