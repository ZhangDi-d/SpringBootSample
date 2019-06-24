package com.ryze.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xueLai on 2019/6/24.
 */
public class AtomicIntegerExample {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        System.out.println("atomicInteger==>" + atomicInteger.get());
        increment();
        System.out.println("atomicInteger==>" + getCount());
    }

    public static void increment() {
        atomicInteger.incrementAndGet();
    }

    public static int getCount() {
        return atomicInteger.get();
    }

}
