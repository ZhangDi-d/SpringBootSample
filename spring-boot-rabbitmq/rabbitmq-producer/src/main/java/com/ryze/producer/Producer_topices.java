package com.ryze.producer;

import com.ryze.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sound.midi.Receiver;


/**
 * Created by xueLai on 2019/8/27.
 * 无效
 */
@Component
public class Producer_topices  {
    private final static Logger logger = LoggerFactory.getLogger(Producer_topices.class);
    private final RabbitTemplate rabbitTemplate;
    public Producer_topices(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
