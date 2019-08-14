package com.ryze.mvc.core;


import com.ryze.ioc.bean.AnnotationApplicationContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by xueLai on 2019/8/8.
 */
public class DispatcherHandler extends SimpleChannelInboundHandler<Object> {

    private AnnotationApplicationContext annotationApplicationContext;
    private FullHttpRequest request;
    private FullHttpResponse response;
    private Channel channel;

    public DispatcherHandler(AnnotationApplicationContext annotationApplicationContext){
        this.annotationApplicationContext = annotationApplicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
