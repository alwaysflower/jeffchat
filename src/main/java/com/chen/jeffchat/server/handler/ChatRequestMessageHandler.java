package com.chen.jeffchat.server.handler;

import com.alibaba.fastjson.JSON;
import com.chen.jeffchat.POJO.ChatRequestMessage;
import com.chen.jeffchat.POJO.ChatResponse;
import com.chen.jeffchat.server.session.Session;
import com.chen.jeffchat.POJO.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Autowired
    private Session session;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatRequestMessage chatRequestMessage) throws Exception {
        String fromId = chatRequestMessage.getFromId();
        String toId = chatRequestMessage.getToId();
        String content = chatRequestMessage.getContent();
        Channel channel = session.getChannel(toId);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String time = dateTime.format(formatter);
        String contentFrom = time + "   你: " + "\n" + content;
        String contentTo = time + "   " + fromId + ": " + "\n" + content;

        if (channel == null){
            System.out.println("该用户不存在或不在线");
        }else {
            System.out.println("向" + toId + "发送消息");
            Response response = new ChatResponse(Response.ChatResponse,true, contentFrom);
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));

            response = new ChatResponse(Response.ChatResponse,true, contentTo);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
        }
    }
}
