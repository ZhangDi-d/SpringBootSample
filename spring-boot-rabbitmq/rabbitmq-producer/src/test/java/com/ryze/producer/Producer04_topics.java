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
public class Producer04_topics {
    private final static Logger logger = LoggerFactory.getLogger(Producer04_topics.class);

    private final static String EXCHANGE_TOPICS = "exchange_topics";
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
            channel.exchangeDeclare(EXCHANGE_TOPICS, BuiltinExchangeType.TOPIC);

            /*声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_EMAIl, true, false, false, null);
            channel.queueDeclare(QUEUE_SMS, true, false, false, null);
            //channel.txSelect(); 开启事务
            //通配符 不绑定队列到交换机
//            channel.queueBind(QUEUE_EMAIl, EXCHANGE_TOPICS, QUEUE_EMAIl);
//            channel.queueBind(QUEUE_SMS, EXCHANGE_TOPICS, QUEUE_SMS);

            /*发布信息
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             */

            //发布到email
            for (int i = 1; i < 10; i++) {
                String message = "Producer04_topics send message " + i;
                channel.basicPublish(EXCHANGE_TOPICS, "message.email", null, message.getBytes("utf-8"));
                logger.info("Producer04_topics console message.email====>" + message);
            }
            //sms
            for (int i = 1; i < 10; i++) {
                String message = "Producer04_topics send message " + i;
                channel.basicPublish(EXCHANGE_TOPICS, "message.sms", null, message.getBytes("utf-8"));
                logger.info("Producer04_topics console message.sms====>" + message);
            }
            //两者都发送sms
            for (int i = 1; i < 10; i++) {
                String message = "Producer04_topics send message " + i;
                channel.basicPublish(EXCHANGE_TOPICS, "message.sms.email", null, message.getBytes("utf-8"));
                logger.info("Producer04_topics console message.sms.email====>" + message);
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
