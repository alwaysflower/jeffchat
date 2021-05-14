package com.chen.jeffchat.server;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chen.jeffchat.JeffchatApplication;
import com.chen.jeffchat.POJO.ChatRequestMessage;
import com.chen.jeffchat.POJO.LoginRequestMessage;
import com.chen.jeffchat.POJO.Message;
import com.chen.jeffchat.server.handler.ChatRequestMessageHandler;
import com.chen.jeffchat.server.handler.LoginRequestMessageHandler;
import com.chen.jeffchat.server.handler.OutTimeHandler;
import com.chen.jeffchat.server.handler.QuitHandler;
import com.chen.jeffchat.server.session.Session;
import com.chen.jeffchat.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ChatServer implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;
    @Autowired
    QuitHandler quitHandler;
    @Autowired
    private Session session;



    public ChatServer() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        LoginRequestMessageHandler loginRequestMessageHandler = applicationContext.getBean(LoginRequestMessageHandler.class);
                        ChatRequestMessageHandler chatRequestMessageHandler = applicationContext.getBean(ChatRequestMessageHandler.class);
                        OutTimeHandler outTimeHandler = applicationContext.getBean(OutTimeHandler.class);
                        nioSocketChannel.pipeline().addLast(new LoggingHandler());
                        nioSocketChannel.pipeline().addLast(new IdleStateHandler(10, 0, 0));
//                        nioSocketChannel.pipeline().addLast(outTimeHandler);
                        nioSocketChannel.pipeline().addLast(new HttpServerCodec());
                        nioSocketChannel.pipeline().addLast(new HttpObjectAggregator(65536));
                        nioSocketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
                                String jsonString = textWebSocketFrame.text();
                                System.out.println("收到的jsonstring是: " + jsonString);
                                JSONObject jsonObject = JSON.parseObject(jsonString);
                                String type = (String) jsonObject.get("type");
                                Message message = null;
                                switch (Integer.parseInt(type)){
                                    case Message.LoginRequestMessage:
                                        System.out.println("收到登录请求");
                                        String userId = (String) jsonObject.get("userId");
                                        String password = (String) jsonObject.get("password");
                                        message = new LoginRequestMessage(userId, password);
                                        channelHandlerContext.fireChannelRead(message);
                                        break;
                                    case Message.ChatRequestMessage:
                                        String fromId = session.getUserId(channelHandlerContext.channel());
                                        String toId = (String) jsonObject.get("toId");
                                        String content = (String) jsonObject.get("content");
                                        message = new ChatRequestMessage(fromId, toId, content);
                                        channelHandlerContext.fireChannelRead(message);
                                        break;
                                }

                            }
                        });
                        nioSocketChannel.pipeline().addLast(loginRequestMessageHandler);
                        nioSocketChannel.pipeline().addLast(chatRequestMessageHandler);
                        nioSocketChannel.pipeline().addLast(quitHandler);
                    }
                })
                .bind(new InetSocketAddress(8081));
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("netty server started.......");
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
