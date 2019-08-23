package com.ryze;

import java.util.Queue;

/**
 * Created by xueLai on 2019/8/23.
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }

}