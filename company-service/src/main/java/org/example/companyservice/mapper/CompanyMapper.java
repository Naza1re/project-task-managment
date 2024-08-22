package org.example.companyservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.companyservice.dto.request.CompanyRequest;
import org.example.companyservice.dto.response.CompanyResponse;
import org.example.companyservice.model.Company;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMapper {
    private final ModelMapper modelMapper;

    public Company fromRequestToEntity(CompanyRequest request) {
        return modelMapper.map(request, Company.class);
    }
    public CompanyResponse fromEntityToResponse(Company company) {
        return modelMapper.map(company, CompanyResponse.class);
    }
}
