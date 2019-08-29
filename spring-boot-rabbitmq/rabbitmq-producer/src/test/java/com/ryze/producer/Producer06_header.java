package com.ryze.producer;

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
 * routing 生产者  交换机模式-direct
 * <p>
 * 用户通知，当用户充值成功或转账完成系统通知用户，通知方式有短信、邮件多种方法 。
 */
public class Producer06_header {
    private final static Logger logger = LoggerFactory.getLogger(Producer06_header.class);

    private final static String EXCHANGE_HEADER = "exchange_header";
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
            channel.exchangeDeclare(EXCHANGE_HEADER, BuiltinExchangeType.HEADERS);

            /*声明队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             */
            channel.queueDeclare(QUEUE_EMAIl, true, false, false, null);
            channel.queueDeclare(QUEUE_SMS, true, false, false, null);

            //headers绑定队列
            Map<String, Object> headers_email = new HashMap<>();
            headers_email.put("inform_type", "email");
            Map<String, Object> headers_sms = new HashMap<>();
            headers_sms.put("inform_type", "sms");
            channel.queueBind(QUEUE_EMAIl, EXCHANGE_HEADER, "", headers_email);
            channel.queueBind(QUEUE_SMS, EXCHANGE_HEADER, "", headers_sms);

            //发布到email
            for (int i = 1; i < 10; i++) {
                String message = "email inform to user" + i;
                Map<String, Object> headers = new HashMap<>();
                headers.put("inform_type", "email");//匹配email通知消费者绑定的header
                //headers.put("inform_type", "sms");//匹配sms通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(headers);
                //Email通知
                channel.basicPublish(EXCHANGE_HEADER, "", properties.build(), message.getBytes());
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
