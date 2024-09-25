package org.example.companyservice.service;

import org.example.companyservice.model.Company;
import org.example.companyservice.security.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface CompanyService {
    Company createCompany(Company company);

    List<Company> getAllCompany();

    Company getCompanyById(String id);

    Company updateCompanyById(String id, Company request);

    Company deleteCompanyById(String id);

    User extractUserInfo(Jwt jwt);
}
