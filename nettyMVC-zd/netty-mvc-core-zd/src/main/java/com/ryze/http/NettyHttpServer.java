package com.ryze.http;

import com.ryze.ioc.bean.AnnotationApplicationContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by xueLai on 2019/8/8.
 * 单例 : 饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。
 * <p>
 * ## 饿汉式和懒汉式区别:
 * <p>
 * 从名字上来说，饿汉和懒汉，
 * 饿汉就是类一旦加载，就把单例初始化完成，保证getInstance的时候，单例是已经存在的了，
 * 而懒汉比较懒，只有当调用getInstance的时候，才回去初始化这个单例。
 * 另外从以下两点再区分以下这两种方式：
 * <p>
 * 1、线程安全：
 * 饿汉式天生就是线程安全的，可以直接用于多线程而不会出现问题，
 * 懒汉式本身是非线程安全的，为了实现线程安全有几种写法，分别是上面的1、2、3，这三种实现在资源加载和性能方面有些区别。
 * <p>
 * 2、资源加载和性能：
 * 饿汉式在类创建的同时就实例化一个静态对象出来，不管之后会不会使用这个单例，都会占据一定的内存，但是相应的，在第一次调用时速度也会更快，因为其资源已经初始化完成，
 * 而懒汉式顾名思义，会延迟加载，在第一次使用该单例的时候才会实例化对象出来，第一次调用时要做初始化，如果要做的工作比较多，性能上会有些延迟，之后就和饿汉式一样了。
 */
public class NettyHttpServer {
    private static int port = 8081;

    private NettyHttpServer() {
    }

    private static NettyHttpServer instance = new NettyHttpServer();

    public static NettyHttpServer getInstance() {
        return instance;
    }

    public void start(AnnotationApplicationContext applicationContext) throws InterruptedException{
        // boss 线程组不断从客户端接受连接，但不处理，由 worker 线程组对连接进行真正的处理,一个线程组其实也能完成，推荐使用两个
        EventLoopGroup boss  = new NioEventLoopGroup();
        EventLoopGroup worker  = new NioEventLoopGroup();
        try{
            //服务端启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ServerInitializer(applicationContext));
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("服务端启动,端口="+port);
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
