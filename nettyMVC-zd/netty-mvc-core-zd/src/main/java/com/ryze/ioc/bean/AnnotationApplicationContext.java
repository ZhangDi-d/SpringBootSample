package com.ryze.ioc.bean;



import com.ryze.http.NettyHttpServer;

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

    public AnnotationApplicationContext(String configuration) {
        super(configuration);
        String path  = xmlUtil.handlerXMLForScanPackage(configuration);

        //执行包的扫描操作
        scanPackage(path);
        //注册bean
        registerBean();
        //把对象创建出来，忽略依赖关系
        doCreateBean();
        //执行容器管理实例对象运行期间的依赖装配
        diBean();
        //mvc 相关注解扫描
        mappingMVC();
        //启动 netty 服务器
        NettyHttpServer instance = NettyHttpServer.getInstance();
        try {
            instance.start(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mappingMVC() {
    }

    private void diBean() {
    }

    private void doCreateBean() {
    }

    private void registerBean() {
    }

    private void scanPackage(String path) {
    }
}
