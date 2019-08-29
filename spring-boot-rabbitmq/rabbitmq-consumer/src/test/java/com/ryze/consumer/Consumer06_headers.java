package com.ryze.consumer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by xueLai on 2019/8/29.
 */
public class Consumer06_headers {
    private final static Logger logger = LoggerFactory.getLogger(Consumer06_headers.class);
    /*
     * 声明Exchange_fanout_inform交换机。
     * 声明两个队列并且绑定到此交换机，绑定时不需要指定routingkey
     * 发送消息时不需要指定routingkey
     */
    private final static String EXCHANGE_HEADER = "exchange_header";
    private final static String QUEUE_EMAIL = "queue_email";

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
            channel.exchangeDeclare(EXCHANGE_HEADER, BuiltinExchangeType.HEADERS);

            /* 声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_EMAIL, true, false, false, null);
            Map<String, Object> headers_email = new HashMap<>();
            headers_email.put("inform_email", "email");

            //交换机和队列绑定
            channel.queueBind(QUEUE_EMAIL,EXCHANGE_HEADER,"",headers_email);
            //指定消费队列
            channel.basicConsume(QUEUE_EMAIL, true, getDefaultConsumer(channel));

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
                logger.info("Consumer06_headers receive message=" + s);
            }
        };
    }
}
