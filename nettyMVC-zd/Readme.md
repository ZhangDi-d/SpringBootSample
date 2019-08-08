### 项目简介:
在 Netty Http 的基础上加入路由和 IOC 等功能，来实现一个 MVC 框架。

- NettyMVC 是一个 MVC 框架，它实现了 IOC 容器的相关功能。
- 支持 @Controller，@RequestParam，@RequestMapping 等 MVC 注解。
- 支持 @Service，@Repositry，@Autowired 等 IOC 注解。
- URI 路由解析，参数映射。
- Request 中支持多种参数类型，包括基本数据类型，List，Array，Map等等。

![](https://images.gitbook.cn/525084e0-b205-11e9-8e6b-6f3263a19060)

### 类与类
1.convertor包下:
com.ryze.convertor.BaseSimpleTypeConverter
com.ryze.convertor.ITypeConverter
com.ryze.convertor.PrimitiveType
com.ryze.convertor.PriTypeConverter

2.http包下:
com.ryze.http.HttpUtil  --Http工具类
com.ryze.http.NettyHttpServer   --服务启动类
com.ryze.http.ServerInitializer 

3.ioc包下:
com.ryze.ioc.annotation:
    com.ryze.ioc.annotation.Autowired
    com.ryze.ioc.annotation.Component
    com.ryze.ioc.annotation.Controller
    com.ryze.ioc.annotation.Repository
    com.ryze.ioc.annotation.Service
com.ryze.ioc.bean:
    com.ryze.ioc.bean.AnnotationApplicationContext
    com.ryze.ioc.bean.ApplicationContext
com.ryze.ioc.factory:
    com.ryze.ioc.factory.BeanFactory
com.ryze.ioc.xml:
    com.ryze.ioc.xml.XmlUtil
    
4.mvc包下:
com.ryze.mvc.annotation:
    com.ryze.mvc.annotation.RequestMapping
    com.ryze.mvc.annotation.RequestParam
com.ryze.mvc.core:
    com.ryze.mvc.core.DispatcherHandler
com.ryze.mvc.util:
    com.ryze.mvc.util.GenericsUtil
    com.ryze.mvc.util.RequestParseUtil































#### 备注: copyRight 归原作者所有
这个项目是一个学习项目,出处为:
https://gitbook.cn/books/5d37b3c1a4d90b6ed9f8fa3c/index.html#-5