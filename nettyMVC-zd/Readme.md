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

### Note:
1.handler()和childHandler():
handler()和childHandler()的主要区别是，handler()是发生在初始化的时候，childHandler()是发生在客户端连接之后。
也就是说，如果需要在客户端连接前的请求进行handler处理，则需要配置handler(),如果是处理客户端连接之后的handler,则需要配置在childHandler()。

2.option与childoption的区别:
option主要是针对boss线程组，child主要是针对worker线程组;
option主要是设置的ServerChannel的一些选项，而childOption主要是设置的ServerChannel的子Channel的选项。如果是Bootstrap的话，只会有option而没有childOption，所以设置的是客户端Channel的选项。

childHandler / childOption / childAttr 方法（只有服务端ServerBootstrap才有child类型的方法）

3.
channelOption含义
  
  ChannelOption.SO_BACKLOG 
  ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
  
  ChannelOption.SO_REUSEADDR 
  ChanneOption.SO_REUSEADDR对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口，比如，某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使用，比如某个进程非正常退出，该程序占用的端口可能要被占用一段时间才能允许其他进程使用，而且程序死掉以后，内核一需要一定的时间才能够释放此端口，不设置SO_REUSEADDR就无法正常使用该端口。
  
  ChannelOption.SO_KEEPALIVE 
  Channeloption.SO_KEEPALIVE参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
  
  ChannelOption.SO_SNDBUF和ChannelOption.SO_RCVBUF 
  ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
  
  ChannelOption.SO_LINGER 
  ChannelOption.SO_LINGER参数对应于套接字选项中的SO_LINGER,Linux内核默认的处理方式是当用户调用close()方法的时候，函数返回，在可能的情况下，尽量发送数据，不一定保证会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送
  
  ChannelOption.TCP_NODELAY 
  ChannelOption.TCP_NODELAY参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关。Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，虽然该方式有效提高网络的有效负载，但是却造成了延时，而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输。
  
  ChannelOption.ALLOCATOR 
  在4.x版本中，UnpooledByteBufAllocator是默认的allocator，尽管其存在某些限制。现在PooledByteBufAllocator已经广泛使用一段时间，并且我们有了增强的缓冲区泄漏追踪机制， 所以是时候让PooledByteBufAllocator成为默认了。 
  总结：Netty4使用对象池，重用缓冲区
  
  ChannelOption.SO_KEEPALIVE 
  当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。


























#### 备注:
这个项目是一个学习项目,出处为:
https://gitbook.cn/books/5d37b3c1a4d90b6ed9f8fa3c/index.html#-5