package com.ryze.consumer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by xueLai on 2019/8/27.
 * workqueue 消费者
 */
public class Consumer01_workqueues {
    private final static Logger logger = LoggerFactory.getLogger(Consumer01_workqueues.class);

    private final static String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);
        //channel.basicConsume(QUEUE, true, getDefaultConsumer(channel));

        //channel.basicQos(1)指该消费者在接收到队列里的消息但没有返回确认结果之前,它不会将新的消息分发给它。
        /*  prefetchSize：消息的大小
            prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
            global：是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
            备注：据说prefetchSize 和global这两项，rabbitmq没有实现，暂且不研究
            void basicQos(int prefetchSize, int prefetchCount, boolean global) throws IOException;
        */

        channel.basicQos(1);

        /*
         * It's a common mistake to miss the *basicAck*. It's an easy error, but the consequences are serious.
         * Messages will be redelivered when your client quits (which may look like random redelivery),
         * but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
         */

        boolean autoAck = true; // acknowledgment is covered below

        /*queue 队列名
         *autoAck 是否自动确认消息,true自动确认,false 不自动要手动调用,建立设置为false
         *consumerTag 消费者标签，用来区分多个消费者
         */
        channel.basicConsume(QUEUE, autoAck, deliverCallback, consumerTag -> { });
    }

    private static Consumer getDefaultConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body, "utf-8");
                logger.info("Consumer01_workqueues receive message=" + s);
            }
        };
    }

    private static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        logger.info("Consumer01_workqueues [x] Received '" + message + "'");
        try {
            doWork(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("Consumer01_workqueues [x] Done");
        }
    };

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }

}
