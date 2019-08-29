package com.ryze.consumer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by xueLai on 2019/8/29.
 */
public class Consumer03_routing_email {
    private final static Logger logger = LoggerFactory.getLogger(Consumer03_routing_email.class);
    /*
     * 声明Exchange_fanout_inform交换机。
     * 声明两个队列并且绑定到此交换机，绑定时不需要指定routingkey
     * 发送消息时不需要指定routingkey
     */
    private final static String EXCHANGE_ROUTING = "exchange_routing";
    private final static String QUEUE_EMAIl = "queue_email";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(Constants.LOCALHOST);
            factory.setPort(Constants.RABBIT_PORT);
            factory.setUsername(Constants.USERNAME_GUEST);
            factory.setPassword(Constants.PASSWORD_GUEST);
            factory.setVirtualHost(Constants.DEFAULT_VIRTUALHOST);

            connection = factory.newConnection();
            channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_ROUTING, BuiltinExchangeType.DIRECT);

            /* 声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_EMAIl, true, false, false, null);

            /*将队列绑定到交换机上
             * queueBind(String queue, String exchange, String routingKey)
             */
            channel.queueBind(QUEUE_EMAIl, EXCHANGE_ROUTING, QUEUE_EMAIl);
            /*
             * 监听队列String queue, boolean autoAck,Consumer callback
             * 参数明细
             * 1、队列名称
             * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动回复
             * 3、消费消息的方法，消费者接收到消息后调用此方法
             */
            channel.basicConsume(QUEUE_EMAIl, true, getDefaultConsumer(channel));

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static Consumer getDefaultConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
             * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String s = new String(body, "utf-8");
                logger.info("Consumer03_routing_email receive message=" + s);
            }
        };
    }
}
