package org.example.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String name;
    private List<String> referenceTaskIds;
    private String status;
}
