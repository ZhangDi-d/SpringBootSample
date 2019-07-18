package com.ryze.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;

/**
 * Created by xueLai on 2019/7/17.
 *
 * WebSocket 连接建立的过程：
 *
 * 1.客户端向服务端发送 http 报文格式的请求，而且是 GET 方法的请求，不过这里与普通的 http 请求有稍微不同的地方，
 *      那就是头部 Connection 字段值是 Upgrade，然后有Upgrade字段，值是 websocket 。通过这些信息将 http 协议升级为 websocket 协议。
 * 2.由服务器端向客户端返回特定格式的 http 报文，表示当前 websocket 建立。
 * 3.当连接建立以后，那么双方就可以通过刚刚建立连接的 socket 来发送数据了，这里发送的数据必须经过 websocket 的帧格式的编码，具体它的信息就要去看 websocket 的协议标准了。
 *
 *
 */
public class WebServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());

            ChannelFuture sync = serverBootstrap.bind(8899).sync();
            sync.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
