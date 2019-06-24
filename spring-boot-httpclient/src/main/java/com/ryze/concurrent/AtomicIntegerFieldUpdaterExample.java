package com.ryze.concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by xueLai on 2019/6/24.
 *
 * （1）字段必须是volatile类型的！
 * （2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。也就是说调用者能够直接操作对象字段，那么就可以反射进行原子操作。但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
 * （3）只能是实例变量，不能是类变量，也就是说不能加static关键字。
 * （4）只能是可修改变量，不能使final变量，因为final的语义就是不可修改。实际上final的语义和volatile是有冲突的，这两个关键字不能同时存在。
 * （5）对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
 *
 * https://img-blog.csdn.net/20180702151647232?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3p4MjAxNTIxNjg1Ng==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70
 */
public class AtomicIntegerFieldUpdaterExample {
    class DemoData{
        public volatile int value1 = 1;
        volatile int value2 = 2;
        protected volatile int value3 = 3;
        private volatile int value4 = 4;
    }
    AtomicIntegerFieldUpdater<DemoData> getUpdater(String fieldName) {
        return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
    }
    void doit() {
        DemoData data = new DemoData();
        System.out.println("1 ==> "+getUpdater("value1").getAndSet(data, 10));
        System.out.println("3 ==> "+getUpdater("value2").incrementAndGet(data));
        System.out.println("2 ==> "+getUpdater("value3").decrementAndGet(data));
        System.out.println("true ==> "+getUpdater("value4").compareAndSet(data, 4, 5));
    }
    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterExample demo = new AtomicIntegerFieldUpdaterExample();
        demo.doit();

        /**
         * value4是不可以修改的
         * Exception in thread "main" java.lang.RuntimeException: java.lang.IllegalAccessException:
         * Class com.ryze.concurrent.AtomicIntegerFieldUpdaterExample can not access a member of class
         * com.ryze.concurrent.AtomicIntegerFieldUpdaterExample$DemoData with modifiers "private volatile"
         */
    }
}
