package org.example.taskservice.rabbit.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${spring.rabbitmq.my-queue.name}")
    private String queueTask;

    @Value("${spring.rabbitmq.exchange.name}")
    private String taskExchange;

    @Bean
    public Queue queueTask() {
        return new Queue(queueTask, false);
    }

    @Bean
    public Exchange exchangeTask() {
        return new TopicExchange(taskExchange, false, false);
    }

    @Bean
    Binding binding(Queue queueTask, Exchange exchangeTask) {
        return BindingBuilder.bind(queueTask).to(exchangeTask).with("first.key").noargs();
    }

}
