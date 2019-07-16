package com.ryze.chapter2;

/**
 * Created by xueLai on 2019/7/16.
 */
public class ThreadGroupName implements Runnable {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("printGroup");
        Thread t1 = new Thread(threadGroup,new ThreadGroupName(), "t1");
        Thread t2 = new Thread(threadGroup,new ThreadGroupName(), "t2");
        t1.start();
        t2.start();
        System.out.println(threadGroup.activeCount()); //获取活动线程的总数,预估值
        threadGroup.list();//打印线程信息
    }

    public void run() {
        String s = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        while (true) {
            System.out.println("iam "+s);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/**
 * output:
 * 2
 * java.lang.ThreadGroup[name=printGroup,maxpri=10]
 *     Thread[t1,5,printGroup]
 *     Thread[t2,5,printGroup]
 * iam printGroup-t1
 * iam printGroup-t2
 */
