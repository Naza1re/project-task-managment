package org.example.companyservice.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
    private String companyName;
    private String companyCeo;
    private String officeAddress;
    private String dataOfFoundation;
}
