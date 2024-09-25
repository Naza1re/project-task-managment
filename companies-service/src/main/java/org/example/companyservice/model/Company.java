package org.example.companyservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "company")
public class Company {

    @Id
    private String id;
    private String companyName;
    private String companyCeo;
    private String officeAddress;
    private String dataOfFoundation;
}
