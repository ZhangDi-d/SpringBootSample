### 1 NIO的SelectionKey(选择键):
#### 1.1 要点

- 是一个抽象类,表示selectableChannel在Selector中注册的标识.每个Channel向Selector注册时,都将会创建一个selectionKey
- 选择键将Channel与Selector建立了关系,并维护了channel事件.
- 可以通过cancel方法取消键,取消的键不会立即从selector中移除,而是添加到cancelledKeys中,在下一次select操作时移除它.所以在调用某个key时,需要使用isValid进行校验.

#### 1.2 操作属性
- OP_ACCEPT:连接可接受操作,仅ServerSocketChannel支持
- OP_CONNECT:连接操作,Client端支持的一种操作
- OP_READ/OP_WRITE

![](https://img-blog.csdn.net/20150813165705122?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

### 2 ByteBuffer

一个ByteBuffer的使用过程是这样的：
1. byteBuffer = ByteBuffer.allocate(N);　　　　//创建
2. readableByteChannel.read(byteBuffer);　　 //读取数据，写入byteBuffer
3. byteBuffer.flip(); 　　　　　　　　　　　　　//变读为写
4. writableByteChannel.write(byteBuffer); 　　//读取byteBuffer，写入数据


### 3. 线程池
线程池不建议使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
 说明：Executors各个方法的弊端：
      1、 newFixedThreadPool和newSingleThreadExecutor:
       主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
      2、newCachedThreadPool和newScheduledThreadPool:
       主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
这里介绍三种创建线程池的方式：

Example 1：
```

//org.apache.commons.lang3.concurrent.BasicThreadFactory
ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
```
 Example2:
 ```
ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
//Common Thread Pool
ExecutorService pool = new ThreadPoolExecutor(5, 200,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
pool.execute(()-> System.out.println(Thread.currentThread().getName()));
pool.shutdown();//gracefully shutdown
```

Example 3:
```
<bean id="userThreadPool" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
        <property name="corePoolSize" value="10" />
        <property name="maxPoolSize" value="100" />
        <property name="queueCapacity" value="2000" />
        <property name="threadFactory" value= threadFactory />
        <property name="rejectedExecutionHandler">
            <ref local="rejectedExecutionHandler" />
        </property>
    </bean>
    //in code
    userThreadPool.execute(thread);
```

工具类:
```java
public class ThreadPoolHelper {

    private static final Logger logger = Logger.getLogger(ThreadPoolHelper.class);

    private static final int POOL_SIZE = 40;//线程池大小

    //订单任务线程池

    private static ThreadPoolExecutor comitTaskPool =(ThreadPoolExecutor) new ScheduledThreadPoolExecutor(POOL_SIZE,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());


    /**
     * 执行订单任务
     *
     * @param comitTask
     */
    public static void executeTask(Runnable comitTask) {
        comitTaskPool.execute(comitTask);
        logger.debug("【线程池任务】线程池中线程数：" + comitTaskPool.getPoolSize());
        logger.debug("【线程池任务】队列中等待执行的任务数：" + comitTaskPool.getQueue().size());
        logger.debug("【线程池任务】已执行完任务数：" + comitTaskPool.getCompletedTaskCount());
    }


    /**
     * 关闭线程池
     */
    public static void shutdown() {
        logger.debug("shutdown comitTaskPool...");
        comitTaskPool.shutdown();
        try {
            if (!comitTaskPool.isTerminated()) {
                logger.debug("直接关闭失败[" + comitTaskPool.toString() + "]");
                comitTaskPool.awaitTermination(3, TimeUnit.SECONDS);
                if (comitTaskPool.isTerminated()) {
                    logger.debug("成功关闭[" + comitTaskPool.toString() + "]");
                } else {
                    logger.debug("[" + comitTaskPool.toString() + "]关闭失败，执行shutdownNow...");
                    if (comitTaskPool.shutdownNow().size() > 0) {
                        logger.debug("[" + comitTaskPool.toString() + "]没有关闭成功");
                    } else {
                        logger.debug("shutdownNow执行完毕，成功关闭[" + comitTaskPool.toString() + "]");
                    }
                }
            } else {
                logger.debug("成功关闭[" + comitTaskPool.toString() + "]");
            }
        } catch (InterruptedException e) {
            logger.warn("接收到中断请" + comitTaskPool.toString() + "停止操作");
        }
    }
}

```