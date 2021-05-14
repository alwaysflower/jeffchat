package com.chen.jeffchat.server.handler;

import com.alibaba.fastjson.JSON;
import com.chen.jeffchat.POJO.LoginRequestMessage;
import com.chen.jeffchat.POJO.LoginResponse;
import com.chen.jeffchat.POJO.Response;
import com.chen.jeffchat.server.ChatServer;
import com.chen.jeffchat.server.session.Session;
import com.chen.jeffchat.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    @Autowired
    private UserService userService;
    @Autowired
    private Session session;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        String userId = loginRequestMessage.getUserId();
        String password = loginRequestMessage.getPassword();
        //todo
        //判断重复登录
        boolean logged = userService.loggedCheck(userId);
        if (logged){
            Response response = new LoginResponse(Response.LoginResponse, false, "不能重复登录");
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
            return;
        }
        boolean login = userService.loginCheck(userId, password);
        if (login){
            session.bind(channelHandlerContext.channel(), userId);
            Response response = new LoginResponse(Response.LoginResponse, true, "登录成功");
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
        } else {
            Response response = new LoginResponse(Response.LoginResponse, false, "登录失败");
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
        }
    }
}
