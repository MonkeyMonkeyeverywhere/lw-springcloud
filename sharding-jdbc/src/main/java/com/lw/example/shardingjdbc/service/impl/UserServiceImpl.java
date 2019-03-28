package com.lw.example.shardingjdbc.service.impl;

import com.dangdang.ddframe.rdb.sharding.api.HintManager;
import com.lw.example.shardingjdbc.dao.UserRepository;
import com.lw.example.shardingjdbc.entity.User;
import com.lw.example.shardingjdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }
}
