package org.example.taskservice.repository;

import org.example.taskservice.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> findByProjectId(String projectId);
    List<Task> findAllByUserId(String userId);


}
