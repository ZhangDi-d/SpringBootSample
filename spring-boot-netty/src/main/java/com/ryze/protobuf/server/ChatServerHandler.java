package com.ryze.protobuf.server;

import com.ryze.protobuf.ChatInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by xueLai on 2019/7/19.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<ChatInfo.Chat> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat chat) throws Exception {
        System.out.println("客户端发送过来的消息 = " + chat.getMsg());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
