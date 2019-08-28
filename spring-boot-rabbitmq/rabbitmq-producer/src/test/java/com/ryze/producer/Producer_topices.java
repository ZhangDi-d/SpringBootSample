package com.ryze.producer;

import com.ryze.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by xueLai on 2019/8/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer_topices {
    private final static Logger logger = LoggerFactory.getLogger(Producer_topices.class);
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.QUEUE_INFORM_EMAIL, "inform.sms.email", message);
            logger.info("Send Message is:'" + message + "'");
        }
    }
}
