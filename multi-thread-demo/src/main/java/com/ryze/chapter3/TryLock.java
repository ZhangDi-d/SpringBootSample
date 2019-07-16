package com.ryze.chapter3;

import jdk.nashorn.internal.ir.TryNode;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xueLai on 2019/7/16.
 */
public class TryLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    int i;

    /**
     * 控制加锁顺序
     *
     * @param i
     */
    public TryLock(int i) {
        this.i = i;
    }

    public void run() {
        if (i == 1) {
            while (true) {
                if (lock1.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " my job done");
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            }
        } else {
            while (true) {
                if (lock2.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " my job done");
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock tryLock1 = new TryLock(1);
        TryLock tryLock2 = new TryLock(2);
        Thread thread1 = new Thread(tryLock1,"thread1");
        Thread thread2 = new Thread(tryLock2,"thread2");
        thread1.start();
        thread2.start();
    }
}
/**
 * output:
 * thread2 my job done
 * thread1 my job done
 */

