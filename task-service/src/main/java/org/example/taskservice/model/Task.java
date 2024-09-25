package org.example.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.taskservice.model.status.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String projectId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime closeAt;
    private LocalDateTime lastUpdatedAt;
    private String lastChangedBy;
    private String name;
    private List<String> referenceTaskIds;
    private Status status;
}
