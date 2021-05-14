package com.chen.jeffchat.controller;

import com.chen.jeffchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public boolean loginCheck(@RequestParam("userId") String userId, @RequestParam("password") String password){

        return userService.loginCheck(userId, password);
    }

}
