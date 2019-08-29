package com.ryze.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by xueLai on 2019/8/29.
 * routing 生产者  交换机模式-direct
 * <p>
 * 用户通知，当用户充值成功或转账完成系统通知用户，通知方式有短信、邮件多种方法 。
 */
public class Producer03_routing {
    private final static Logger logger = LoggerFactory.getLogger(Producer03_routing.class);

    private final static String EXCHANGE_ROUTING = "exchange_routing";
    private final static String QUEUE_EMAIl = "queue_email";
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
            channel.exchangeDeclare(EXCHANGE_ROUTING, BuiltinExchangeType.DIRECT);

            /*声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_EMAIl, true, false, false, null);
            channel.queueDeclare(QUEUE_SMS, true, false, false, null);

            /*将队列绑定到交换机上
             * queueBind(String queue, String exchange, String routingKey)
             */
            channel.queueBind(QUEUE_EMAIl, EXCHANGE_ROUTING, QUEUE_EMAIl);
            channel.queueBind(QUEUE_SMS, EXCHANGE_ROUTING, QUEUE_SMS);

            /*发布信息
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             */
            for (int i = 1; i < 10; i++) {
                String message = "Producer03_routing send message " + i;
                channel.basicPublish(EXCHANGE_ROUTING, QUEUE_EMAIl, null, message.getBytes("utf-8"));
                logger.info("Producer03_routing console QUEUE_EMAIl====>" + message);
            }

            for (int i = 1; i < 10; i++) {
                String message = "Producer03_routing send message " + i;
                channel.basicPublish(EXCHANGE_ROUTING, QUEUE_SMS, null, message.getBytes("utf-8"));
                logger.info("Producer03_routing console QUEUE_SMS====>" + message);
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                } finally {
                    channel = null;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection = null;
                }
            }
        }

    }

}
