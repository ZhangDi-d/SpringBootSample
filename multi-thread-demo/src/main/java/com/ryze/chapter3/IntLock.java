package com.ryze.chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xueLai on 2019/7/16.
 * 锁中断的死锁
 */
public class IntLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    /**
     * 控制加锁顺序
     *
     * @param lock
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();//锁可以被中断
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName()+"线程退出.");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        IntLock intLock1 = new IntLock(1);
        IntLock intLock2 = new IntLock(2);
        Thread thread1 = new Thread(intLock1,"thread1");
        Thread thread2 = new Thread(intLock2,"thread2");
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread2.interrupt();
    }
}

