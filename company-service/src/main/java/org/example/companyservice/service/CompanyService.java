package org.example.companyservice.service;

import org.example.companyservice.dto.CompanyListResponse;
import org.example.companyservice.dto.request.CompanyRequest;
import org.example.companyservice.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest companyRequest);

    CompanyListResponse getAllCompany();

    CompanyResponse getCompanyById(String id);

    CompanyResponse updateCompanyById(String id,CompanyRequest request);

    CompanyResponse deleteCompanyById(String id);
}
