package com.ryze.chapter2;

/**
 * Created by xueLai on 2019/7/16.
 */
public class NoVisibility {
    //volatile告诉虚拟机,这个变量会被不同的线程修改
    private static volatile boolean ready;//默认为false
    private static int number;//默认为0

    private static class ReadThread implements Runnable {
        public void run() {
            while(!ready){};
            System.out.println("number=" + number);//ReadThread不会看到主线程的修改,所以不会打印出number

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ReadThread());
        thread.start();
        Thread.sleep(1000);
        number = 10;
        ready = true;
        Thread.sleep(10000);
    }
}
