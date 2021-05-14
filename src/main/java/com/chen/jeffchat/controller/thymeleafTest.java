package com.chen.jeffchat.controller;

import com.chen.jeffchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class thymeleafTest {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String test(){
        return "hello";
    }

}
