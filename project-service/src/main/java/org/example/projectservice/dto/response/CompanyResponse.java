package org.example.projectservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
    private String id;
    private String companyCeo;
    private String officeAddress;
    private String dataOfFoundation;
}
