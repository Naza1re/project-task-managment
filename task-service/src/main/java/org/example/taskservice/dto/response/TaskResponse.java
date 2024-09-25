package org.example.taskservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private String id;
    private String projectId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime closeAt;
    private String name;
    private List<String> referenceTaskIds;
    private String status;
}
