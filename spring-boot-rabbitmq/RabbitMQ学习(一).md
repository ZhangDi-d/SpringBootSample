
## 1.RabbitMQ工作模式
RabbitMQ有以下几种工作模式 ：
1、Work queues 
2、Publish/Subscribe (fanout)
3、Routing (direct) 
4、Topics (topics)
5、Headers(headers)
6、RPC

### work queues
![](https://www.rabbitmq.com/img/tutorials/python-two.png)
与入门程序相比，多了一个消费端，两个消费端共同消费同一个队列中的消息。
应用场景：对于 任务过重或任务较多情况使用工作队列可以提高任务处理的速度。
测试：
1、使用入门程序，启动多个消费者。
2、生产者发送多个消息。
结果：
1、一条消息只会被一个消费者接收；
2、rabbit采用轮询的方式将消息是平均发送给消费者的；
3、消费者在处理完某条消息后，才会收到下一条消息。


### Publish/Subscribe
![](https://www.rabbitmq.com/img/tutorials/exchanges.png)
发布订阅模式：
1、每个消费者监听自己的队列。
2、生产者将消息发给broker，由交换机将消息转发到绑定此交换机的每个队列，每个绑定交换机的队列都将接收到消息

### Routing
![](https://www.rabbitmq.com/img/tutorials/direct-exchange.png)
路由模式：
1、每个消费者监听自己的队列，并且设置routingkey。
2、生产者将消息发给交换机，由交换机根据routingkey来转发消息到指定的队列。

Routing模式和Publish/subscibe有啥区别？ (使用routing完全可以实现Publish/subscibe 的功能)
Routing模式要求队列在绑定交换机时要指定routingkey，消息会转发到符合routingkey的队列


### Topics
![](https://www.rabbitmq.com/img/tutorials/python-five.png)
 **代码中,通配符模式下,生产者不需要将队列绑定到交换机.(发布订阅 路由模式需要queueBind(),工作队列也不需要绑定队列到交换机)**
路由模式：
1、每个消费者监听自己的队列，并且设置带统配符的routingkey。
2、生产者将消息发给broker，由交换机根据routingkey来转发消息到指定的队列。


### Header 
header模式与routing不同的地方在于，header模式取消routingkey，使用header中的 key/value（键值对）匹配
队列。

## 2. RabbitMQ如何保证消息不丢失
### 生产者弄丢了数据
RabbitMQ 生产者将数据发送到 rabbitmq 的时候,可能数据在网络传输中搞丢了，这个时候 RabbitMQ 收不到消息，消息就丢了。
RabbitMQ 提供了两种方式来解决这个问题：
#### 事务方式：
在生产者发送消息之前，通过 channel.txSelect 开启一个事务，接着发送消息，如果消息没有成功被 RabbitMQ 接收到，生产者会收到异常，此时就可以进行事务回滚 channel.txRollback 然后重新发送。假如 RabbitMQ 收到了这个消息，就可以提交事务 channel.txCommit。
但是这样一来，生产者的吞吐量和性能都会降低很多，现在一般不这么干。

#### confirm 机制：
这个 confirm 模式是在生产者那里设置的，就是每次写消息的时候会分配一个唯一的 id，然后 RabbitMQ 收到之后会回传一个 ack，告诉生产者这个消息 ok 了。如果 rabbitmq 没有处理到这个消息，那么就回调一个 nack 的接口，这个时候生产者就可以重发。

事务机制和 cnofirm 机制最大的不同在于事务机制是同步的，提交一个事务之后会阻塞在那儿，但是 confirm 机制是异步的，发送一个消息之后就可以发送下一个消息，然后那个消息 rabbitmq 接收了之后会异步回调你一个接口通知你这个消息接收到了。

所以一般在生产者这块避免数据丢失，都是用 confirm 机制的。


### Rabbitmq 弄丢了数据
RabbitMQ 集群也会弄丢消息，这个问题在官方文档的教程中也提到过，就是说在消息发送到 RabbitMQ 之后，默认是没有落地磁盘的，万一 RabbitMQ 宕机了，这个时候消息就丢失了。

所以为了解决这个问题，RabbitMQ 提供了一个持久化的机制，消息写入之后会持久化到磁盘，哪怕是宕机了，恢复之后也会自动恢复之前存储的数据，这样的机制可以确保消息不会丢失。

设置持久化有两个步骤:
 - 第一个是创建 queue 的时候将其设置为持久化的，这样就可以保证 rabbitmq 持久化 queue 的元数据，但是不会持久化 queue 里的数据；
 - 第二个是发送消息的时候将消息的 deliveryMode 设置为 2，就是将消息设置为持久化的，此时 rabbitmq 就会将消息持久化到磁盘上去。
但是这样一来可能会有人说：万一消息发送到 RabbitMQ 之后，还没来得及持久化到磁盘就挂掉了，数据也丢失了。

对于这个问题，其实是配合上面的 confirm 机制一起来保证的，就是在消息持久化到磁盘之后才会给生产者发送 ack 消息。万一真的遇到了那种极端的情况，生产者是可以感知到的，此时生产者可以通过重试发送消息给别的 RabbitMQ 节点

### 消费者弄丢了数据
RabbitMQ 消费端弄丢了数据的情况是这样的：在消费消息的时候，刚拿到消息，结果进程挂了，这个时候 RabbitMQ 就会认为你已经消费成功了，这条数据就丢了。

对于这个问题，要先说明一下 RabbitMQ 消费消息的机制：在消费者收到消息的时候，会发送一个 ack 给 RabbitMQ，告诉 RabbitMQ 这条消息被消费到了，这样 RabbitMQ 就会把消息删除。
但是默认情况下这个发送 ack 的操作是自动提交的，也就是说消费者一收到这个消息就会自动返回 ack 给 RabbitMQ，所以会出现丢消息的问题。
所以针对这个问题的解决方案就是：关闭 RabbitMQ 消费者的自动提交 ack,在消费者处理完这条消息之后再手动提交 ack。
这样即使遇到了上面的情况，RabbitMQ 也不会把这条消息删除，会在你程序重启之后，重新下发这条消息过来。


### 小结:
    生产者弄丢了数据: 事务(channel.txSelect() ,channel.RollBack(),channel.Commit) ,效率低一般不这么做; confirm 机制 
    Rabbitmq 弄丢了数据: queue 设置 持久消息; 发送消息时,将消息设置为持久化( deliveryMode=2)
    消费者弄丢了数据: 处理完消息后手动提交,而不是自动ack
    

## 3. RabbitMQ 中 Exchange与Queue关系
1. exchange 与 queue 是 多对多的关系，一个exchange上可以绑定多个queue；一个queue可以绑定到多个exchange上（多个exchange的类型可以不同，如一个是fanout, 一个是direct，一个是topic)。
2. exchange中的数据分发到哪个绑定的queue中由RoutingKey决定，但是​exchange上绑定哪些queue是由程序（或spring配置）确定的。
3. 消息发送端可以选择发到指定queue或exchange中，但是消费者连接的肯定是queue,不能直接监听exchange。
4. rabbitmq的queue跟其它mq服务器一样，可以有多个监听者，但是一个消息只能由一个监听者消费。​



## 4.RabbitMQ 常用方法

### channel.exchangeDeclare()
```text
Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete,Map<String, Object> arguments) throws IOException;
```

exchange：交换机名称
type：   
    -fanout: fanout类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中
    -direct: direct类型的Exchange路由规则也很简单，它会把消息路由到那些binding key与routing key完全匹配的Queue中。
    -topic: 规则就是模糊匹配，可以通过通配符满足一部分规则就可以传送。它的约定是：
            routing key为一个句点号“. ”分隔的字符串（我们将被句点号“. ”分隔开的每一段独立的字符串称为一个单词），如“stock.usd.nyse”、“nyse.vmw”、“quick.orange.rabbit” binding key与routing key一样也是句点号“. ”分隔的字符串。
            binding key中可以存在两种特殊字符“”与“#”，用于做模糊匹配，其中“”用于匹配一个单词，“#”用于匹配多个单词(可以是零个）
durable：是否开启持久化exchange
autoDelete： 当已经没有消费者时，服务器是否可以删除该exchange
arguments： 扩展参数

### channel.basicQos()
```text
void basicQos(int prefetchSize, int prefetchCount, boolean global) throws IOException;
```
参数：
prefetchSize：消息的大小
prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
global：是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别


###  channel.basicPublish()

```text
void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body) throws IOException;
```
exchange：名称
routingKey：路由键，#匹配0个或多个单词，*匹配一个单词，在topic exchange做消息转发用
mandatory：为true时如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返还给生产者。为false时出现上述情形broker会直接将消息扔掉
immediate：为true时如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
props：需要注意的是BasicProperties.deliveryMode，1:不持久化 2：持久化 这里指的是消息的持久化，配合channel(durable=true),queue(durable)可以实现，即使服务器宕机，消息仍然保留
body：要发送的信息

### channel.basicAck()
```text
void basicAck(long deliveryTag, boolean multiple) throws IOException;
```
deliveryTag：该消息的index
multiple：是否批量处理.true:将一次性ack所有小于deliveryTag的消息

### channel.basicNack()

```text
void basicNack(long deliveryTag, boolean multiple, boolean requeue) throws IOException;
```
deliveryTag:该消息的index
multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息
requeue：被拒绝的是否重新入队列 注意：如果设置为true ，则会添加在队列的末端

### channel.basicConsume()
```text
String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
```
queue：队列名称
autoAck：是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel.basicReject 进行消息应答
callback：回调函数


### chanel.exchangeBind()
```text
Exchange.BindOk exchangeBind(String destination, String source, String routingKey) throws IOException;
```
生产者发送消息到source交换器中，source根据路由键找到与其匹配的另一个交换器destination，并把消息转发到destination中，存储在destination绑定的队列queue中

### channel.queueDeclare()
```text
Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments) throws IOException;
```
queue: 队列名称
durable： 是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常，一般等于true的话用于一个队列只能有一个消费者来消费的场景
autodelete：当没有任何消费者使用时，自动删除该队列
arguments：扩展参数。如：x-message-ttl


### channel.queueBind()
```text
AMQP.Queue.BindOk queueBind(String queue , String exchange , String routingKey ) throws IOException;
```
queue 队列名称
exchange 交换机名称
routingKey 路由key



更多:
https://docs.spring.io/spring-amqp/reference/html/
https://www.rabbitmq.com/tutorials/tutorial-six-java.html