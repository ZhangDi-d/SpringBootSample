package com.ryze.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by xueLai on 2019/7/10.
 */
@Aspect
@Component
public class DataSourceAop {
    @Pointcut("!@annotation(com.ryze.annotation.Master) && (execution(* com.ryze..service..*.select*(..))" +
            "||execution(* com.ryze..service..*.get*(..))execution(* com.ryze..service..*.find*(..)))")
    public void readPointcut() {

    }

    @Pointcut()
    public void writePointcut() {

    }
}
