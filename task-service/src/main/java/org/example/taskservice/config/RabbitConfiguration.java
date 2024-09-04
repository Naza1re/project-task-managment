package org.example.taskservice.config;

import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.my-queue.name}")
    private String queueTask;

    @Bean
    public Queue queue() {
        return new Queue(queueTask, false);
    }
}
