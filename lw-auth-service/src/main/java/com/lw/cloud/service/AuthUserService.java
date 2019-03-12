package com.lw.cloud.service;

import com.lw.cloud.entity.User;
import com.lw.cloud.query.AuthQuery;

public interface AuthUserService {
    User auth(AuthQuery query);
}
