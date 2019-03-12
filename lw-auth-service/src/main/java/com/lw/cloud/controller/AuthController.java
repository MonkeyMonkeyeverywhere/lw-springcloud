package com.lw.cloud.controller;

import com.lw.cloud.base.ResponseData;
import com.lw.cloud.entity.User;
import com.lw.cloud.query.AuthQuery;
import com.lw.cloud.service.AuthUserService;
import com.lw.cloud.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("oauth")
public class AuthController {

    @Autowired
    AuthUserService authUserService;

    @PostMapping("token")
    public ResponseData auth(@RequestBody AuthQuery query){
        Assert.notNull(query.getAccessKey(), "AccessKey 不能为空");
        Assert.notNull(query.getSecretKey(), "SecretKey 不能为空");
        User user = authUserService.auth(query);
        if (user == null) {
            return ResponseData.failByParam("认证失败");
        }
        JWTUtils jwt = JWTUtils.getInstance();
        String token = jwt.getToken(String.valueOf(user.getId()));
        return ResponseData.ok(token);
    }

    @GetMapping("token")
    public ResponseData oauth(@RequestBody AuthQuery query){
        Assert.notNull(query.getAccessKey(), "AccessKey 不能为空");
        Assert.notNull(query.getSecretKey(), "SecretKey 不能为空");
        User user = authUserService.auth(query);
        if (user == null) {
            return ResponseData.failByParam("认证失败");
        }
        JWTUtils jwt = JWTUtils.getInstance();
        String token = jwt.getToken(String.valueOf(user.getId()));
        return ResponseData.ok(token);
    }
}
