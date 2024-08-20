package org.example.taskservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private String id;
    private String projectId;
    private String userId;
    private String name;
    private List<String> referenceTaskIds;
    private String status;
}
