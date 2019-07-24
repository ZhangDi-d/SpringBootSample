package com.ryze.netty.oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xueLai on 2019/7/24.
 */
public class OldioServer {

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
            new Thread(new OldioServerHandler(socket)).start();

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
