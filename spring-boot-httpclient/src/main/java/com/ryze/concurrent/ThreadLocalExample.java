package com.ryze.concurrent;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Created by xueLai on 2019/6/24.
 * 1.ThreadLocal 中key为弱引用,value为强引用
 * 所以，如果 ThreadLocal 没有被外部强引用的情况下，在垃圾回收的时候会 key 会被清理掉，而 value 不会被清理掉。这样一来，ThreadLocalMap 中就会出现key为null的Entry。假如我们不做任何措施的话，value 永远无法被GC 回收，这个时候就可能会产生内存泄露。
 * 使用完 ThreadLocal方法后 最好手动调用remove()方法.
 *
 * 2.强引用:只要引用存在，垃圾回收器永远不会回收
 *
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
