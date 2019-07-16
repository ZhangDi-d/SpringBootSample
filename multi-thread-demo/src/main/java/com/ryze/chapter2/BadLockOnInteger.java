package com.ryze.chapter2;

/**
 * Created by xueLai on 2019/7/16.
 * 错误的加锁示例
 * 在java中,Integer属于不变对象,对象一旦创建,就不能被修改.
 * 修改这个问题,只需要将加锁对象换成badLockOnInteger即可
 */
public class BadLockOnInteger implements Runnable {
    private static Integer i = 0; //加锁对象
    private static BadLockOnInteger badLockOnInteger = new BadLockOnInteger();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(badLockOnInteger);
        Thread thread2 = new Thread(badLockOnInteger);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }

    public void run() {
        for (int j = 0; j < 1000000; j++) {
            synchronized (i) {
                i++;
            }
        }
    }
}
