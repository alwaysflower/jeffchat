package com.chen.jeffchat.server.handler;

import com.chen.jeffchat.server.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private Session session;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String userId = session.getUserId(ctx.channel());
        session.unbind(ctx.channel(), userId);
        System.out.println(userId + "正常断开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String userId = session.getUserId(ctx.channel());
        session.unbind(ctx.channel(), userId);
        System.out.println(userId + "因异常断开");
    }
}
