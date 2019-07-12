package com.ryze.bean;

import com.ryze.enums.DBTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xueLai on 2019/7/10.
 * 通过ThreadLocal将数据源设置到每个线程上下文中
 */
public class DBContextHolder {
    private final static Logger logger = LoggerFactory.getLogger(DBContextHolder.class);
    private final static ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        contextHolder.set(DBTypeEnum.MASTER);
        logger.info("DBContextHolder::=============> master");
    }

    public static void slave1() {
        contextHolder.set(DBTypeEnum.SLAVE1);
        logger.info("DBContextHolder::=============> slave1");
    }

    /*public static void slave() {
        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            set(DBTypeEnum.SLAVE1);
            System.out.println("切换到slave1");
        }else {
            set(DBTypeEnum.SLAVE2);
            System.out.println("切换到slave2");
        }
    }*/
}
