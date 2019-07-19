### 1.学习 
学习:https://gitbook.cn/books/5b739c2f5013f02ab02a16d5/index.html

### 2.测试websocket
启动WebSocket服务器,再右键index.html->Run 'index.html' 就可以测试websocket服务器了.

### 3.Channel、ChannelPipeline、ChannelHandler、ChannelHandlerContext 之间的关系

Netty 中的 Channel 是框架自己定义的一个通道接口，Netty 实现的客户端 NIO 套接字通道是 NioSocketChannel，提供的服务器端 NIO 套接字通道是 NioServerSocketChannel。

当服务端和客户端建立一个新的连接时， 一个新的 Channel 将被创建，同时它会被自动地分配到它专属的 ChannelPipeline。

ChannelPipeline 是一个拦截流经 Channel 的入站和出站事件的 ChannelHandler 实例链，并定义了用于在该链上传播入站和出站事件流的 API。那么就很容易看出这些 ChannelHandler 之间的交互是组成一个应用程序数据和事件处理逻辑的核心。

ChannelHandlerContext 代表了 ChannelHandler 和 ChannelPipeline 之间的关联，每当有 ChannelHandler 添加到 ChannelPipeline 中时，都会创建 ChannelHandlerContext。

ChannelHandlerContext 的主要功能是管理它所关联的 ChannelHandler 和在同一个 ChannelPipeline 中的其他 ChannelHandler 之间的交互。事件从一个 ChannelHandler 到下一个 ChannelHandler 的移动是由 ChannelHandlerContext 上的调用完成的。
![](https://images.gitbook.cn/d4632330-a5e9-11e8-9c44-a333cdfc1b85)

### 4.netty线程模型

Netty 线程模型是典型的 Reactor 模型结构，其中常用的 Reactor 线程模型有三种，分别为：Reactor 单线程模型、Reactor 多线程模型和主从 Reactor 多线程模型。
#### Reactor 线程模型
##### Reactor 单线程模型
![](https://images.gitbook.cn/da9c4ec0-a5e9-11e8-9c44-a333cdfc1b85)
```java
EventLoopGroup bossGroup = new NioEventLoopGroup(1);
ServerBootstrap b = new ServerBootstrap();
b.group(bossGroup)
 .channel(NioServerSocketChannel.class)
...

```

##### Reactor 多线程模型

![](https://images.gitbook.cn/e0adce10-a5e9-11e8-9c44-a333cdfc1b85)

```java
EventLoopGroup bossGroup = new NioEventLoopGroup(1);
EventLoopGroup workerGroup = new NioEventLoopGroup();
ServerBootstrap b = new ServerBootstrap();
b.group(bossGroup, workerGroup)
 .channel(NioServerSocketChannel.class)
 ...

```
##### 主从 Reactor 多线程模型
![](https://images.gitbook.cn/e5e89c70-a5e9-11e8-9c44-a333cdfc1b85)
```java
EventLoopGroup bossGroup = new NioEventLoopGroup(4);
EventLoopGroup workerGroup = new NioEventLoopGroup();
ServerBootstrap b = new ServerBootstrap();
b.group(bossGroup, workerGroup)
 .channel(NioServerSocketChannel.class)
 ...
```

服务器端的 ServerSocketChannel 只绑定到了 bossGroup 中的一个线程，因此在调用 Java NIO 的 Selector.select 处理客户端的连接请求时，实际上是在一个线程中的，所以对只有一个服务的应用来说，bossGroup 设置多个线程是没有什么作用的，反而还会造成资源浪费。


### 5.EventLoopGroup 和 EventLoop
- NioEventLoopGroup 实际上就是个线程池，一个 EventLoopGroup 包含一个或者多个 EventLoop；
- 一个 EventLoop 在它的生命周期内只和一个 Thread 绑定；
- 所有 EnventLoop 处理的 I/O 事件都将在它专有的 Thread 上被处理；
- 一个 Channel 在它的生命周期内只注册于一个 EventLoop；
- 每一个 EventLoop 负责处理一个或多个 Channel；


### 6.粘包与拆包示例演示与解决方案
TCP 是一个面向字节流的协议，所谓流就是没有界限的一串数据，你无法知道什么时候开始，什么时候结束。TCP 底层并不了解上层业务数据的具体含义，它会根据 TCP 缓冲区的实际情况进行包的划分，所以在业务上认为的一个完整的包可能会被 TCP 拆分成多个包进行发送，也有可能把多个小的包封装成一个大的数据包发送，这就是所谓的 TCP 粘包拆包问题。

#### 粘包问题的解决策略
由于 TCP 底层并不了解上层业务数据的具体含义，所以在底层是无法保证数据包不被拆分和重组的，这个问题只能通过应用层来解决。根据业界的主流协议的解决方案，归纳如下：

- 消息定长，例如每个报文的大小固定长度为 200 字节，如果不足，空位补空格；
- 在包尾增加回车换行符进行分割，例如 FTP 协议；
- 将消息分为消息头和消息体，消息头中包含表示消息总长度（或消息体长度）的字段，通常设计思路为消息头的第一个字段使用 int32 来表示消息的总长度；
- 使用自定义协议。

Netty 已经帮我们做好了，完全的开箱即用:
LineBasedFrameDecoder：基于换行符的解码器
DelimiterBasedFrameDecoder：基于分隔符的解码器
FixedLengthFrameDecoder：固定长度的解码器
LengthFieldBasedFrameDecoder：基于长度域解码器
```java
pipeline.addLast(new LineBasedFrameDecoder(1024));
```

### 7.protobuf
https://developers.google.cn/protocol-buffers/docs/javatutorial





















