package com.ryze.chapter3;

/**
 * Created by xueLai on 2019/7/16.
 * 普通的死锁
 */
public class CommonDeadLockDemo  {
    private static final Object object1 = new Object();
    private static final Object object2 = new Object();
    static class Thread1 implements Runnable{

        public void run() {
            synchronized (object1){
                System.out.println(Thread.currentThread().getName()+"持有object1..");
                try {
                    Thread.sleep(2000);
                    synchronized(object2){
                        System.out.println(Thread.currentThread().getName()+"持有object2..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class  Thread2 implements Runnable{

        public void run() {
            synchronized (object2){
                System.out.println(Thread.currentThread().getName()+"持有object2..");
                try {
                    Thread.sleep(2000);
                    synchronized(object1){
                        System.out.println(Thread.currentThread().getName()+"持有object1..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Thread1(),"thread1");
        Thread thread2 = new Thread(new Thread2(),"thread2");
        thread1.start();
        thread2.start();

    }
}
