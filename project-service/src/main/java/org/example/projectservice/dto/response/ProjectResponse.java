package org.example.projectservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private String companyId;
    private String status;
}
