package org.example.taskservice.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
@RequiredArgsConstructor
public class TaskSender {

    @Value("${spring.rabbitmq.my-queue.name}")
    private String queueTask;

    private final AmqpTemplate amqpTemplate;

    public void sendTask(String task) {
        amqpTemplate.convertAndSend(queueTask,task);
    }
}
