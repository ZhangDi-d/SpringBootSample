### 服务端创建步骤：

- 创建两个 NioEventLoopGroup 实例，bossGroup 用于服务端接受客户端的连接，workerGroup 用于进行客户端连接 SocketChannel 的网络读写；
- 创建 ServerBootstrap 的实例，ServerBootstrap 是启动服务端的辅助类，目的是降低服务端的开发复杂度；
- 调用 ServerBootstrap 的 group 方法，将两个线程组实例当作参数传递到 ServerBootstrap 中；
- 设置创建的 Channel 类型为 NioServerSocketChannel，它的功能主要是负责创建子 Channel，这些子 Channel 代表已接受的连接，这里的 channel() 参数类型为 Class，内部是通过反射来实现 Channel 的创建，内部持有 java.nio.channels.ServerSocketChannel 的引用；
- 绑定 I/O 事件的处理类 childHandler，主要用于处理网络 IO 事件，例如对消息编解码、业务处理等；
- 调用 bind 方法绑定监听端口，链式调用同步阻塞方法 sync 等待绑定操作完成，返回的 ChannelFuture 用于异步操作的通知回调；
- 使用 future.channel().closeFuture().sync(); 进行阻塞，直到服务端 Channel 关闭；
- finally{} 中进行优雅退出，释放相关资源。

### 客户端创建步骤：

- 创建客户端处理 I/O 读写的 NioEventLoopGroup 线程组；
- 创建客户端辅助启动类 Bootstrap 实例；
- Channel 的配置与服务端不同的是需要设置为 NioSocketChannel；
- 添加 handler，使用匿名内部类 ChannelInitializer 的实例，实现它的抽象方法 initChannel，向 Pipeline 中添加我们自己的业务处理 handler；
- 调用 connect 方法发起异步连接，调用同步方法等待连接成功；
- 其他跟服务端类似。