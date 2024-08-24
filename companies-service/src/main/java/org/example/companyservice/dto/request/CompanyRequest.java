package org.example.companyservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
    private String companyName;
    private String companyCeo;
    private String officeAddress;
    private String dataOfFoundation;
}
