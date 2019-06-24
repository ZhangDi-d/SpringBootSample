package com.ryze.concurrent;

/**
 * Created by xueLai on 2019/6/24.
 */
public class DeadLockExample {
    private static Object object1 = new Object();
    private static Object object2 = new Object();

    public static void main(String[] args){
        new Thread(new Runnable() {
            public void run() {
                synchronized (object1){
                    System.out.println(Thread.currentThread()+" get object1... ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread()+" waiting object2... ");
                    synchronized (object2){
                        System.out.println(Thread.currentThread()+" get object2... ");
                    }
                }
            }
        },"thread-1").start();
        new Thread(new Runnable() {
            public void run() {
                synchronized (object2){
                    System.out.println(Thread.currentThread()+" get object2... ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread()+" waiting object1... ");
                    synchronized (object1){
                        System.out.println(Thread.currentThread()+" get object1... ");
                    }
                }
            }
        },"thread-2").start();
    }
}
