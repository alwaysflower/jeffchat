package com.chen.jeffchat.service;

import io.netty.channel.Channel;
import com.chen.jeffchat.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Session session;

    @Override
    public boolean loginCheck(String userId, String password) {

        return true;
    }

    @Override
    public boolean loggedCheck(String userId) {

        return session.getChannel(userId) != null;
    }

    @Override
    public Channel getChannel(String userId) {

        return session.getChannel(userId);
    }

}
