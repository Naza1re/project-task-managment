package org.example.projectservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "projects")
public class Project {

    @Id
    private String id;
    private String name;
    private String description;
    private String companyId;


}
