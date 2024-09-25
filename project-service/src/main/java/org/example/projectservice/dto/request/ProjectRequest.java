package org.example.projectservice.dto.request;

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
public class ProjectRequest {
    private String name;
    private String description;
    private String companyId;
}
