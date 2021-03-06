package com.ryze.consumer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by xueLai on 2019/8/29.
 */
public class Consumer02_publishsubscribe_sms {
    private final static Logger logger = LoggerFactory.getLogger(Consumer02_publishsubscribe_sms.class);
    /*
     * 声明Exchange_fanout_inform交换机。
     * 声明两个队列并且绑定到此交换机，绑定时不需要指定routingkey
     * 发送消息时不需要指定routingkey
     */
    private final static String EXCHANGE_FANOUT = "exchange_fanout";
    private final static String QUEUE_SMS = "queue_sms";

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
            channel.exchangeDeclare(EXCHANGE_FANOUT, BuiltinExchangeType.FANOUT);

            /* 声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_SMS, true, false, false, null);

            /*将队列绑定到交换机上
             * queueBind(String queue, String exchange, String routingKey)
             */
            channel.queueBind(QUEUE_SMS, EXCHANGE_FANOUT, "");

            channel.basicConsume(QUEUE_SMS, true, getDefaultConsumer(channel));

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
                logger.info("Consumer02_publishsubscribe_sms receive message=" + s);
            }
        };
    }
}
