package com.ryze.netty.oio2;

import com.ryze.netty.oio.OldioServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xueLai on 2019/7/24.
 * <p>
 * 网络编程的基本模型是 Client/Server，也就是两个进程之间进行相互通信，其中服务端提供位置信息（绑定的 IP 地址和监听端口），
 * 客户端通过连接操作向服务端监听的地址发起连接请求，通过三次握手建立连接，如果连接成功，双方就可以通过 Socket 进行通信。
 * 在基于传统同步阻塞模型开发中，ServerSocket 负责绑定 IP 地址，启动监听端口；Socket 负责发起连接操作。连接成功后，
 * 双方通过输入输出流进行同步阻塞式通信。
 */
public class OldioServer {

    private OioServerHandlerExecutePool executePool = new OioServerHandlerExecutePool(20, 10000);
    private int port;

    public OldioServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        OldioServer oldioServer = new OldioServer(9092);
        oldioServer.serve();

    }

    public void serve() {
        ServerSocket serverSocket = null;
        System.out.println("OioServer 已启动，监听端口：" + port);

        try {
            serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("接收的连接来自 " + socket);
            executePool.execute(new OldioServerHandler(socket));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
