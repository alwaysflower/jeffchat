package com.chen.jeffchat.service;

import io.netty.channel.Channel;

public interface UserService {
    boolean loginCheck(String userId, String password);
    boolean loggedCheck(String userId);
    public Channel getChannel(String userId);
}
