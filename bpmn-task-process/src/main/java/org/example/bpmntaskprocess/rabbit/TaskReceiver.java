package org.example.bpmntaskprocess.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bpmntaskprocess.service.TaskProcessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskReceiver {

    private final TaskProcessService taskProcessService;

    @Value("${spring.rabbitmq.queue.name}")
    static final String taskQueue = "taskQueue";


    @RabbitListener(queues = taskQueue)
    public void listen(String taskId) {
        log.info("Received task id {}", taskId);
        taskProcessService.createTask(taskId);

    }


}
