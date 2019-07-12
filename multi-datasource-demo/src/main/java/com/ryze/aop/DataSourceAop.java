package com.ryze.aop;

import com.ryze.bean.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xueLai on 2019/7/10.
 */
@Aspect
@Component
public class DataSourceAop {
    private final static Logger Logger = LoggerFactory.getLogger(DataSourceAop.class);

    /**
     * execution (* com.ryze.service.impl..*. select*(..))
     *  execution(* com..*.*Dao.find*(..))
     */
    @Pointcut("!@annotation(com.ryze.annotation.Master) && (execution (* com.ryze.service.impl..*.select*(..))" +
            "||execution (* com.ryze.service.impl..*.get*(..))||execution (* com.ryze.service.impl..*.find*(..)))")
    public void readPointcut() {
        Logger.info("enter readPointcut..");
    }

    @Pointcut("@annotation(com.ryze.annotation.Master) " +
            "|| execution(* com.ryze.service.impl..*.insert*(..)) " +
            "|| execution(* com.ryze.service.impl..*.add*(..)) " +
            "|| execution(* com.ryze.service.impl..*.update*(..)) " +
            "|| execution(* com.ryze.service.impl..*.edit*(..)) " +
            "|| execution(* com.ryze.service.impl..*.delete*(..)) " +
            "|| execution(* com.ryze.service.impl..*.remove*(..))")
    public void writePointcut() {
        Logger.info("enter writePointcut..");
    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave1();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
