package org.example.projectservice.service;

import org.example.projectservice.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse getCompanyById(String companyId);
}
