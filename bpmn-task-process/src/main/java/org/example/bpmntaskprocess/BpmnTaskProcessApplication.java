package org.example.bpmntaskprocess;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProcessApplication
@SpringBootApplication
public class BpmnTaskProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmnTaskProcessApplication.class, args);
	}

}
