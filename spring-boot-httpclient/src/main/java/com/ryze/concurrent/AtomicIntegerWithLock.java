package com.ryze.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xueLai on 2019/7/1.
 *
 * 类AtomicIntegerWithLock是线程安全的，此结构中大量使用了Lock对象的lock/unlock方法对。同样可以看到的是对于自增和自减操作使用了++/--。之所以能够保证线程安全，是因为Lock对象的lock()方法保证了只有一个线程能够只有此锁。需要说明的是对于任何一个lock()方法，都需要一个unlock()方法与之对于，通常情况下为了保证unlock方法总是能够得到执行，unlock方法被置于finally块中。
 * 另外这里使用了java.util.concurrent.locks.ReentrantLock.ReentrantLock对象.
 */
public class AtomicIntegerWithLock {
    private int value;

    private Lock lock = new ReentrantLock();

    public AtomicIntegerWithLock() {
        super();
    }

    public AtomicIntegerWithLock(int value) {
        this.value = value;
    }

    public final int get() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }

    public final void set(int newValue) {
        lock.lock();
        try {
            value = newValue;
        } finally {
            lock.unlock();
        }

    }

    public final int getAndSet(int newValue) {
        lock.lock();
        try {
            int ret = value;
            value = newValue;
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public final boolean compareAndSet(int expect, int update) {
        lock.lock();
        try {
            if (value == expect) {
                value = update;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndIncrement() {
        lock.lock();
        try {
            return value++;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndDecrement() {
        lock.lock();
        try {
            return value--;
        } finally {
            lock.unlock();
        }
    }

    public final int incrementAndGet() {
        lock.lock();
        try {
            return ++value;
        } finally {
            lock.unlock();
        }
    }

    public final int decrementAndGet() {
        lock.lock();
        try {
            return --value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return Integer.toString(get());
    }
}
