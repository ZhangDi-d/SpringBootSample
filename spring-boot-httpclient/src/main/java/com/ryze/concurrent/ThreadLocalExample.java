package com.ryze.concurrent;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Created by xueLai on 2019/6/24.
 */
public class ThreadLocalExample implements Runnable {

    /* 非java8写法
    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyyMMdd HHmm");
        }
    };
    */

    private static final ThreadLocal<SimpleDateFormat> FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    @Override
    public void run() {
        System.out.println( Thread.currentThread().getName() + " default Formatter = " + FORMATTER.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //FORMATTER pattern is changed here by thread, but it won't reflect to other threads
        FORMATTER.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        System.out.println(Thread.currentThread().getName() + " formatter = " + FORMATTER.get().toPattern());
    }

    private static ExecutorService getThreadPool(Object obj) {
        ThreadFactory customThreadfactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("FORMATTER-Thread-" + obj).setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        return new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), customThreadfactory);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        for (int i = 0; i < 10; i++) {
            getThreadPool(i).execute(obj);

        }
    }
}
