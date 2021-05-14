package com.chen.jeffchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatroomController {

    @RequestMapping("chatroom")
    public String getChatRoom(){
        return "chatroom";
    }
}
