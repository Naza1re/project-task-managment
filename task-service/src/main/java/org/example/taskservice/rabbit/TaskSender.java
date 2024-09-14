package org.example.taskservice.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
@RequiredArgsConstructor
public class TaskSender {

    @Value("${spring.rabbitmq.my-queue.name}")
    private String queueTask;

    @Value("${spring.rabbitmq.exchange.name}")
    private String taskExchange;

    private final RabbitTemplate rabbitTemplate;

    public void sendTask(String taskId) {
        rabbitTemplate.convertAndSend(taskExchange, "first.key", taskId);
    }
}
