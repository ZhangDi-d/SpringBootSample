package com.ryze.http;

import com.ryze.mvc.core.DispatcherHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by xueLai on 2019/8/8.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private ApplicationContext applicationContext;

    public ServerInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //ChunkedWriteHandler 这个handler主要用于处理大数据流,最好放在最前面
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());
        //HttpServerCodec 是 HttpRequestDecoder 和 HttpReponseEncoder 的组合，编码和解码的 handler
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //用POST方式请求服务器的时候，对应的参数信息是保存在message body中的,如果只是单纯的用HttpServerCodec是无法完全的解析Http POST请求的，
        // 因为HttpServerCodec只能获取uri中参数，所以需要加上HttpObjectAggregator.
        pipeline.addLast("aggregator",new HttpObjectAggregator(65535));
        //自定义的handler
        pipeline.addLast("handler",new DispatcherHandler(applicationContext));

    }
}
