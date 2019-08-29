https://docs.spring.io/spring-amqp/reference/html/

## 1.工作模式
RabbitMQ有以下几种工作模式 ：
1、Work queues
2、Publish/Subscribe
3、Routing
4、Topics
5、Header
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

### Routing

### Topics


## RabbitMQ 常用方法

### 1.channel.exchangeDeclare()
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




