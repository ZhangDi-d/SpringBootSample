package com.ryze.httpServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xueLai on 2019/7/17.
 *
 * bossGroup 和 workerGroup 是两个线程池, 它们默认线程数为 CPU 核心数乘以 2，
 * bossGroup 用于接收客户端传过来的请求，接收到请求后将后续操作交由 workerGroup 处理。
 *
 * 服务启动辅助类的实例 bootstrap:boostrap 用来为 Netty 程序的启动组装配置一些必须要组件，例如上面的创建的两个线程组。
 * channel 方法用于指定服务器端监听套接字通道 NioServerSocketChannel，其内部管理了一个 Java NIO 中的ServerSocketChannel实例。
 *
 * channelHandler 方法用于设置业务职责链，责任链是我们下面要编写的，责任链具体是什么，它其实就是由一个个的 ChannelHandler 串联而成，
 * 形成的链式结构。正是这一个个的 ChannelHandler 帮我们完成了要处理的事情。
 *
 * bootstrap 的 bind 方法将服务绑定到 8080 端口上，bind 方法内部会执行端口绑定等一系列操，使得前面的配置都各就各位各司其职，
 * sync 方法用于阻塞当前 Thread，一直到端口绑定操作完成。接下来一句是应用程序将会阻塞等待直到服务器的 Channel 关闭。
 */
public class HttpServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            //服务端启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}

