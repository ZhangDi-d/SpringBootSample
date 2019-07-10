package com.ryze.aop;

import com.ryze.bean.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by xueLai on 2019/7/10.
 */
@Aspect
@Component
public class DataSourceAop {
    @Pointcut("!@annotation(com.ryze.annotation.Master) && (execution(* com.ryze..service..*.select*(..))" +
            "||execution(* com.ryze..service..*.get*(..))||execution(* com.ryze..service..*.find*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.ryze.annotation.Master) " +
            "|| execution(* com.ryze..*.service..*.insert*(..)) " +
            "|| execution(* com.ryze..*.service..*.add*(..)) " +
            "|| execution(* com.ryze..*.service..*.update*(..)) " +
            "|| execution(* com.ryze..*.service..*.edit*(..)) " +
            "|| execution(* com.ryze..*.service..*.delete*(..)) " +
            "|| execution(* com.ryze..*.service..*.remove*(..))")
    public void writePointcut() {

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
