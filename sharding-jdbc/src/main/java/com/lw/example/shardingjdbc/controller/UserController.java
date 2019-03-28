package com.lw.example.shardingjdbc.controller;


import cn.hutool.json.JSONUtil;
import com.lw.example.shardingjdbc.entity.User;
import com.lw.example.shardingjdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String listUsers(){
        List<User> users = userService.listAll();
        return JSONUtil.toJsonStr(users);
    }

    @GetMapping("save")
    public String save(){
        User user = new User(1003L,"甘肃","胡波");
        userService.addUser(user);
        return "success";
    }

}
