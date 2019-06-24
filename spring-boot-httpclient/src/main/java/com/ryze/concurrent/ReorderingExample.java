package com.ryze.concurrent;

import java.util.concurrent.*;

/**
 * Created by xueLai on 2019/6/24.
 */
public class ReorderingExample {
    static int x = 0, y = 0, a = 0, b = 0;

//    private static ExecutorService getThreadPool(Object obj) {
//        ThreadFactory customThreadfactory = new CustomThreadFactoryBuilder()
//                .setNamePrefix("Thread-" + obj).setDaemon(false)
//                .setPriority(Thread.MAX_PRIORITY).build();
//        return new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), customThreadfactory);
//    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            x = y = a = b = 0;
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            System.out.println(x + " " + y);
        }
    }
}
