package com.ryze.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


/**
 * Created by xueLai on 2019/8/27.
 */
@Configuration
public class RabbitmqConfig {

    public final static String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public final static String QUEUE_INFORM_SMS = "queue_inform_sms";
    public final static String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    /**
     * 交换机配置
     * ExchangeBuilder提供了fanout、direct、topic、header交换机类型的配置
     *
     * @return the exchange
     */
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange getExchange() {
        //durable(true)持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue queueInformEmailQueue() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue queueInformSmsQueue() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    @Bean
    public Binding bindingQueueInformSms(@Qualifier(QUEUE_INFORM_SMS) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.*.sms.*").noargs();
    }

    @Bean
    public Binding bindingQueueInformEmail(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("inform.*.email.*").noargs();
    }

   /* @Bean("connectionFactory")
    public CachingConnectionFactory getConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setChannelCacheSize(20);
        return connectionFactory;
    }
    @Bean("rabbitTemplate")
    public RabbitTemplate   getRabbitTemplate(@Qualifier("connectionFactory")CachingConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_TOPICS_INFORM);
        rabbitTemplate.setQueue(QUEUE_INFORM_EMAIL);
        return rabbitTemplate;
    }*/
}
