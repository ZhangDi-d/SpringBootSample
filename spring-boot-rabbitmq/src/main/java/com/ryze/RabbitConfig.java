package com.ryze;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by xueLai on 2019/8/23.
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue Queue() {
        return new ArrayBlockingQueue(1000);
    }

}