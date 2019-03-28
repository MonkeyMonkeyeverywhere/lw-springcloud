package com.lw.example.shardingjdbc.service;

import com.lw.example.shardingjdbc.entity.User;

import java.util.List;

public interface UserService {
    List<User> listAll();

    void addUser(User user);
}
