package org.example.companyservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
    private String id;
    private String companyCeo;
    private String officeAddress;
    private String dataOfFoundation;
}
