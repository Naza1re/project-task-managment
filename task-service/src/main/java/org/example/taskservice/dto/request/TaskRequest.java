package org.example.taskservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    private String projectId;
    private String name;
    private List<String> referenceTaskIds;
    private String description;
    private String status;
}
