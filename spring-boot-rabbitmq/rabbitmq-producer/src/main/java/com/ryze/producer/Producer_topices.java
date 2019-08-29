package com.ryze.producer;

import com.ryze.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by xueLai on 2019/8/27.
 * 不使用测试类发送信息,在构建bean后发送信息
 *
 * @PostConstruct 在bean创建完成并赋值完成后，进行的初始化操作
 * @PreDestroy 在容器销毁bean之前，通知进行销毁操作
 * postProcessBeforeInitialization:在初始化之前工作
 * postProcessAfterInitialization:在初始化之后工作
 */
@Component
public class Producer_topices {
    private final static Logger logger = LoggerFactory.getLogger(Producer_topices.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void sendMessage() {
        for (int i = 0; i < 200; i++) {
            String message = "send sms inform to user" + i;

            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.sms.email", message);
            logger.info("Send Message is:'" + message + "'");
        }
    }


}
