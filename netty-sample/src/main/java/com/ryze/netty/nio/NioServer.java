package com.ryze.netty.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by xueLai on 2019/7/23.
 */
public class NioServer {
    private final static int PORT = 9091;

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
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NioServer 已经启动,监听端口:" + PORT);

        while (true) {
            int select = selector.select();
            System.out.println("start=========>select:" + select);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();// 删除处理过的key
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    // 接受新的连接，并将它注册到选择器
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    System.out.println("接受的连接来自: " + sc);
                    byte[] bytes = ("欢迎来自" + InetAddress.getLocalHost().getHostName()).getBytes();
                    ByteBuffer byteBuff = ByteBuffer.allocate(1024);
                    byteBuff.put(bytes);

                    byteBuff.flip();
                    sc.write(byteBuff);

                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer byteBuff = ByteBuffer.allocate(1024);
                    // 读取客户端发送过来的消息
                    while (true) {
                        byteBuff.clear();
                        int read = 0;
                        try {
                            read = sc.read(byteBuff);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (read == 0) {
                            break;
                        }
                        if (read == -1) {
                            // 将对端链路关闭
                            key.cancel();
                            sc.close();
                            break;
                        }

                        String s = new String(byteBuff.array(), 0, read);
                        System.out.println("客户端发送过来的数据 = " + s);
                        byteBuff.clear();
                        byteBuff.put(("Did you say '" + s + "'?").getBytes());

                        byteBuff.flip();
                        sc.write(byteBuff);
                    }

                } else if (key.isWritable()) {
                    System.out.println("writable...");
                }
            }

        }
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
