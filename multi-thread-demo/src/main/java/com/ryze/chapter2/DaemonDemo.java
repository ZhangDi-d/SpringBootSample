package com.ryze.chapter2;

/**
 * Created by xueLai on 2019/7/16.
 */
public class DaemonDemo {
    public static class DaemonT implements Runnable {
        public void run() {
            while (true) {
                System.out.println("i am still alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonT());
        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(1000); //main线程调用此方法休眠2秒后退出,只剩守护线程了,守护线程也会结束,退出.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
