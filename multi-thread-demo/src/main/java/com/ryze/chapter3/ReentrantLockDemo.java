package com.ryze.chapter3;

import javax.sound.midi.Soundbank;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xueLai on 2019/7/16.
 * 可重入锁仅仅是对于同一个线程而言的,同一个线程加锁多少次,就必须释放多少次锁,否则的话会报错
 */
public class ReentrantLockDemo implements Runnable {

    private static int i = 0;
    private static ReentrantLock lock = new ReentrantLock();

    public void run() {
        for (int j = 0; j < 100000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ReentrantLockDemo());
        Thread thread2 = new Thread(new ReentrantLockDemo());
        thread1.start();
        thread2.start();
        thread1.join();//等待线程thread1执行结束
        thread2.join();//等待线程thread2执行结束
        System.out.println("i=" + i); //输出结果

    }
}
