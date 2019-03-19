package com.lw.cloud.controller;

import com.lw.cloud.base.ResponseData;
import com.lw.cloud.query.LoginQuery;
import com.lw.cloud.service.EnterpriseUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@Api(value = "用户控制器",tags = "用户接口")
public class EnterpriseUserController {

    @Autowired
    private EnterpriseUserService enterpriseUserService;

    @ApiOperation(value = "用户登录",notes = "用户登录企业编号用户编号必填")
    @PostMapping("login")
    @ApiResponses({@ApiResponse(code = 403,message = "无权限访问")})
    @ResponseHeader(name = "myhead",description = "lw")
    public ResponseData login(@Valid @RequestBody LoginQuery query){
        Assert.notNull(query,"参数不能为空" );
        return ResponseData.ok(enterpriseUserService.login(query.getEid(),query.getUid() ));
    }

}
