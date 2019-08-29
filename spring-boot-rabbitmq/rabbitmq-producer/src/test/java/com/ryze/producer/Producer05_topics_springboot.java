package com.ryze.producer;

import com.ryze.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xueLai on 2019/8/28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendByTopics() {
        for (int i = 0; i < 5; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message);
            System.out.println("Send Message is:'" + message + "'");
        }
    }
}
