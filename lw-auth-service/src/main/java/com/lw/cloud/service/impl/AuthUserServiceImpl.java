package com.lw.cloud.service.impl;

import com.lw.cloud.entity.User;
import com.lw.cloud.query.AuthQuery;
import com.lw.cloud.service.AuthUserService;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Override
    public User auth(AuthQuery query) {
        return new User(1L);
    }
}
