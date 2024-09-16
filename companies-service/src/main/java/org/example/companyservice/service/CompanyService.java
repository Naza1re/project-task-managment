package org.example.companyservice.service;

import org.example.companyservice.dto.response.CompanyListResponse;
import org.example.companyservice.dto.request.CompanyRequest;
import org.example.companyservice.dto.response.CompanyResponse;
import org.example.companyservice.security.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest companyRequest);

    CompanyListResponse getAllCompany();

    CompanyResponse getCompanyById(String id);

    CompanyResponse updateCompanyById(String id,CompanyRequest request);

    CompanyResponse deleteCompanyById(String id);

    User extractUserInfo(Jwt jwt);
}
