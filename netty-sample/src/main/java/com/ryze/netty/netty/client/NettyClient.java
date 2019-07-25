package com.ryze.netty.netty.client;

/**
 * Created by xueLai on 2019/7/25.
 */
public class NettyClient {
    // 客户端要连接的服务端主机名或ip
    private final String host;

    // 客户端要连接的服务端端口号
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {

    }

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 9010);

        nettyClient.connect();
    }

}
