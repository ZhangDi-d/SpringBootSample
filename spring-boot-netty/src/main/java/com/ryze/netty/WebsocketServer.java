package com.ryze.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xueLai on 2019/6/3.
 */
public class WebsocketServer {
    public static void main(String[] args) throws InterruptedException {
        // 初始化主线程池（boss线程池）
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        // 初始化从线程池(worker线程池)
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // 创建服务器启动器
            ServerBootstrap b = new ServerBootstrap();
            // 指定使用主线程池和从线程池
            b.group(mainGroup, subGroup)
                    // 指定使用Nio通道类型
                    .channel(NioServerSocketChannel.class)
                    // 指定通道初始化器加载通道处理器
                    .childHandler(new WsServerInitializer());

            // 绑定端口号启动服务器，并等待服务器启动
            // ChannelFuture是Netty的回调消息
            ChannelFuture future = b.bind(9090).sync();
            // 等待服务器socket关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭boos线程池和worker线程池
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
