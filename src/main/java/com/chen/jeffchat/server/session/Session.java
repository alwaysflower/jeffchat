package com.chen.jeffchat.server.session;


/*会话管理*/


import io.netty.channel.Channel;

public interface Session {
    /*绑定*/
    void bind(Channel channel, String userId);

    void unbind(Channel channel, String userId);

    Channel getChannel(String userId);

    String getUserId(Channel channel);

}
