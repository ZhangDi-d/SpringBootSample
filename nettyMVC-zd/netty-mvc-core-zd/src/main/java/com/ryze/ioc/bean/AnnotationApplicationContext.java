package com.ryze.ioc.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xueLai on 2019/8/8.
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    //保存类路径的缓存
    private List<String> classCache = Collections.synchronizedList(new ArrayList<>());
    //保存需要注入的类的缓存
    private List<Class<?>> beanDefinition = Collections.synchronizedList(new ArrayList<>());
    //保存类实例的容器
    private Map<String, Object> beanFactory = new ConcurrentHashMap<>();
    // 完整路径和 方法的 mapping
    private Map<String, Object> handleMapping = new ConcurrentHashMap<>();
    // 类路径和controller 的 mapping
    public Map<String, Object> controllerMapping = new ConcurrentHashMap<>();

    @Override
    protected Object doGetBean(String objName) {
        return null;
    }
}
