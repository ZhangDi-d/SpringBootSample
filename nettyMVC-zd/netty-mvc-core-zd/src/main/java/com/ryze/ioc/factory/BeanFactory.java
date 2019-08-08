package com.ryze.ioc.factory;

/**
 * Created by xueLai on 2019/8/8.
 * 容器的实现，我们在 IOC 容器启动时，也做了 MVC 注解的扫描，将路径和方法存入Map 中
 */
public abstract class BeanFactory {
    public Object getBean(String objName){
        return doGetBean(objName);
    }
    /**
     *交给子类，容器实现类去完成
     */
    protected abstract Object doGetBean(String objName);
}
