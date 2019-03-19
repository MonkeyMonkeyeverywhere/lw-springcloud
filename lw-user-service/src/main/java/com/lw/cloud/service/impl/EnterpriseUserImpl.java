package com.lw.cloud.service.impl;

import com.lw.cloud.service.EnterpriseUserService;
import com.lw.cloud.util.JWTUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class EnterpriseUserImpl implements EnterpriseUserService {

    JWTUtils jwtUtils = JWTUtils.getInstance(System.getProperty("rsa.modulus"), System.getProperty("rsa.privateExponent"), System.getProperty("rsa.publicExponent"));

    @Override
    public String login(Long eid, String uid) {
        Assert.notNull(eid,"企业id不能为空！" );
        Assert.hasText(uid,"用户id不能为空！" );
        // 暂时不查询数据库判断数据正确性
        String token = jwtUtils.getToken(uid);
        return token;
    }
}
