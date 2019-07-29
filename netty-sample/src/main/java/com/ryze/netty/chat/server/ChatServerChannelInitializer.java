package com.ryze.netty.chat.server;

import com.ryze.netty.chat.ChatInfo;
import com.ryze.netty.chat.server.handler.AuthHandler;
import com.ryze.netty.chat.server.handler.LoginRequestHandler;
import com.ryze.netty.chat.server.handler.SingleChatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by xueLai on 2019/7/29.
 */
public class ChatServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(ChatInfo.Chat.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        //登录请求
        pipeline.addLast(new LoginRequestHandler());
        //验证
        pipeline.addLast(new AuthHandler());
        //一对一聊天
        pipeline.addLast(new SingleChatHandler());


    }
}
