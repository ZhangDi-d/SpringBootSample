package com.ryze.netty.oio;

import com.sun.deploy.util.Waiter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xueLai on 2019/7/24.
 */
public class OldioServerHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public OldioServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        InputStream inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));

        OutputStream outputStream = socket.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
    }

    @Override
    public void run() {
        try {
            writer.println("欢迎" + InetAddress.getLocalHost().getHostAddress() + "!");
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println("msg=" + msg);
                writer.println("你发过来的信息是" + msg + "吗?");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer != null) {
            writer.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
