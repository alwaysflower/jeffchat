package com.chen.jeffchat.server.session;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SessionMemoryImpl implements Session {
    Map<String, Channel> userId2channel = new ConcurrentHashMap<>();
    Map<Channel, String> channel2userId = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String userId) {
        userId2channel.put(userId, channel);
        channel2userId.put(channel, userId);
    }

    @Override
    public void unbind(Channel channel, String userId) {
        userId2channel.remove(userId);
        channel2userId.remove(channel);
    }

    @Override
    public Channel getChannel(String userId) {
        return userId2channel.get(userId);
    }

    @Override
    public String getUserId(Channel channel) {
        return channel2userId.get(channel);
    }

    public void print(){
        System.out.println(userId2channel);
    }
}
