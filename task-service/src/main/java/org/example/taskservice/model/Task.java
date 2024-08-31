package org.example.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskservice.model.status.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String projectId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime closeAt;
    private String name;
    private List<String> referenceTaskIds;
    private Status status;
}
