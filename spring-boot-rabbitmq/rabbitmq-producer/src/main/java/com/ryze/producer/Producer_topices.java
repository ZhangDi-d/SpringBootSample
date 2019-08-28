package com.ryze.producer;

import com.ryze.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by xueLai on 2019/8/27.
 * 无效
 */
@Component
public class Producer_topices {
    private final static Logger logger = LoggerFactory.getLogger(Producer_topices.class);
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void sendMessage() throws InterruptedException {
        Thread.sleep(5000);
        for (int i = 0; i < 5; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.QUEUE_INFORM_EMAIL, "inform.sms.email", message);
            logger.info("Send Message is:'" + message + "'");
        }
    }

}
