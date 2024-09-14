package org.example.bpmntaskprocess.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.name}")
    private String taskQueue;

    @Bean
    public Queue queue() {
        return new Queue(taskQueue, false);
    }
}
