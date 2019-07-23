package com.ryze.netty.nio;

/**
 * Created by xueLai on 2019/7/23.
 */
public class NioServer {
    private final static int PORT = 6789;

    private static void start(){
        // 创建一个选择器来处理Channel
        // 打开服务端套接字通道
        // 设置此通道为非阻塞模式
        // 绑定端口
        // 向给定的选择器注册此通道的接受连接事件
    }

    public static void main(String[] args){
        start();
    }
}
