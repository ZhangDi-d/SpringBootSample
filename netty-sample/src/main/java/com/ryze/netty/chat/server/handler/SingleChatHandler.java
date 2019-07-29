package com.ryze.netty.chat.server.handler;

import com.ryze.netty.chat.ChatInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by xueLai on 2019/7/29.
 */
public class SingleChatHandler extends SimpleChannelInboundHandler<ChatInfo.Chat> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat msg) throws Exception {

    }
}
