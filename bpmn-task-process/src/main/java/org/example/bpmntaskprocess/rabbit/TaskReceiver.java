package org.example.bpmntaskprocess.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskReceiver {

    @Value("${spring.rabbitmq.queue.name}")
    static final String taskQueue = "taskQueue";


    @RabbitListener(queues = taskQueue)
    public void listen(String taskId) {
        System.out.println("Message: " + taskId);
    }


}
