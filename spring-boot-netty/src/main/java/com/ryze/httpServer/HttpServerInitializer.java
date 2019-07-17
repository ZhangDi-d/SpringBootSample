package com.ryze.httpServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by xueLai on 2019/7/17.
 * Netty 是一个高性能网络通信框架，同时它也是比较底层的框架，想要 Netty 支持 Http（超文本传输协议），必须要给它提供相应的编解码器。
 *
 * 我们这里使用 Netty 自带的 Http 编解码组件 HttpServerCodec 对通信数据进行编解码，HttpServerCodec 是 HttpRequestDecoder 和 HttpResponseEncoder 的组合，
 * 因为在处理 Http 请求时这两个类是经常使用的，所以 Netty 直接将他们合并在一起更加方便使用。
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //处理http消息的编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //Netty 为我们提供了一个 HttpObjectAggregator 类，这个 ChannelHandler 作用就是将请求转换为单一的 FullHttpReques
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        //添加自定义的ChannelHandler
        pipeline.addLast("httpServerHandler", new HttpServerHandler());

    }
}
