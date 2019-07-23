package com.ryze.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by xueLai on 2019/7/23.
 */
public class NioServer {
    private final static int PORT = 6789;

    private static void start() throws IOException {
        // 创建一个选择器来处理Channel
        Selector selector = Selector.open();
        // 打开服务端套接字通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 设置此通道为非阻塞模式
        socketChannel.configureBlocking(false);
        // 绑定端口
        socketChannel.bind(new InetSocketAddress(PORT));
        // 向给定的选择器注册此通道的接受连接事件
        socketChannel.register(selector,);
    }

    public static void main(String[] args){
        start();
    }
}
