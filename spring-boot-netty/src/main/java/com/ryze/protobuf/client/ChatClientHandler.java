package com.ryze.protobuf.client;

import com.ryze.protobuf.ChatInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by xueLai on 2019/7/19.
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<ChatInfo.Chat> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for(int i = 0; i < 100; i++) {
            ChatInfo.Chat chat = ChatInfo.Chat.newBuilder()
                    .setMsg("hello-" + i)
                    .build();
            ctx.writeAndFlush(chat);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat chat) throws Exception {
        System.out.println("服务端发送过来的消息内容 = " + chat.getMsg());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
