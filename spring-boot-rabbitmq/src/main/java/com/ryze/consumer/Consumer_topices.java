package com.ryze.consumer;

import com.rabbitmq.client.Channel;
import com.ryze.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by xueLai on 2019/8/27.
 */
@Component
public class Consumer_topices {
    private final static Logger logger = LoggerFactory.getLogger(Consumer_topices.class);

    //监听email队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receiveEmail(String msg, Message message, Channel channel) {
        logger.info("Consumer_topices receiveEmail ===>"+msg);
    }

    //监听sms队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void receiveSms(String msg, Message message, Channel channel) {
        logger.info("Consumer_topices receiveSms ===>"+msg);
    }
}
