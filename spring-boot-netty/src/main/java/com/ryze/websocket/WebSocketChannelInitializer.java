package com.ryze.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by xueLai on 2019/7/17.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler()); //目的是支持异步大文件传输
        pipeline.addLast(new HttpObjectAggregator(65536)); // 目的是将多个消息转换为单一的request或者response对象
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws")); //Netty 提供的 WebSocketServerProtocolHandler 会处理除 TextWebSocketFrame 以外的其他类型帧
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
