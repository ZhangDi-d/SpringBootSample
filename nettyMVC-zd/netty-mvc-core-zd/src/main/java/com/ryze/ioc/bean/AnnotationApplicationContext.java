package com.ryze.ioc.bean;


import com.paul.ioc.annotation.Component;
import com.paul.ioc.annotation.Controller;
import com.paul.ioc.annotation.Repository;
import com.paul.ioc.annotation.Service;
import com.ryze.http.NettyHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xueLai on 2019/8/8.
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationApplicationContext.class);
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
        String path = xmlUtil.handlerXMLForScanPackage(configuration);

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

    /**
     * 根据类路径获得 class 对象
     */
    private void registerBean() {
        if (classCache.isEmpty()) {
            return;
        }
        for (String path : classCache) {
            try {
                //使用反射，通过类路径获取class 对象
                Class<?> clazz = Class.forName(path);
                //找出需要被容器管理的类，比如，@Component，@org.test.demo.Controller，@org.test.demo.Service，@Repository
                if (clazz.isAnnotationPresent(Repository.class) || clazz.isAnnotationPresent(Service.class)
                        || clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Component.class)) {
                    beanDefinition.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描包下面所有的 .class 文件的类路径到上面的List中
     */
    private void scanPackage(final String path) {

        logger.info("scanPackage:===========>path:" + path);
        URL url = this.getClass().getClassLoader().getResource(path.replaceAll("\\.", "/"));
        logger.info("scanPackage:===========>url.getPath():" + url.getPath());
        try {
            File file = new File(url.toURI());
            file.listFiles(new FileFilter() {
                //pathname 表示当前目录下的所有文件
                @Override
                public boolean accept(File pathname) {
                    //递归查找文件
                    if (pathname.isDirectory()) {
                        scanPackage(path + "." + pathname.getName());
                    } else {
                        if (pathname.getName().endsWith(".class")) {
                            String classPath = path + "." + pathname.getName().replace(".class", "");
                            System.out.println("addClassPath:" + classPath);
                            classCache.add(classPath);
                        }
                    }
                    return true;
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
