package org.example.companyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.companyservice.dto.CompanyListResponse;
import org.example.companyservice.dto.request.CompanyRequest;
import org.example.companyservice.dto.response.CompanyResponse;
import org.example.companyservice.exception.CompanyAlreadyExistException;
import org.example.companyservice.exception.CompanyNotFoundException;
import org.example.companyservice.mapper.CompanyMapper;
import org.example.companyservice.model.Company;
import org.example.companyservice.repository.CompanyRepository;
import org.example.companyservice.security.model.User;
import org.example.companyservice.service.CompanyService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.example.companyservice.security.utill.SecurityConstants.*;
import static org.example.companyservice.utill.ExceptionMessages.COMPANY_ALREADY_EXIST;
import static org.example.companyservice.utill.ExceptionMessages.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        checkingCompanyExistByName(companyRequest.getCompanyName());
        Company companyToSave = companyMapper.fromRequestToEntity(companyRequest);
        Company savedCompany = companyRepository.save(companyToSave);
        return companyMapper.fromEntityToResponse(savedCompany);
    }

    @Override
    public CompanyListResponse getAllCompany() {
        List<Company> companyList = companyRepository.findAll();
        List<CompanyResponse> companyResponseList = companyList.stream()
                .map(companyMapper::fromEntityToResponse)
                .toList();
        return new CompanyListResponse(companyResponseList);
    }

    @Override
    public CompanyResponse getCompanyById(String id) {
        Company company = getOrThrow(id);
        return companyMapper.fromEntityToResponse(company);
    }

    @Override
    public CompanyResponse updateCompanyById(String id, CompanyRequest request) {

        Company company = getOrThrow(id);
        Company newCompany = companyMapper.fromRequestToEntity(request);
        newCompany.setId(company.getId());
        Company savedCompany = companyRepository.save(newCompany);
        return companyMapper.fromEntityToResponse(savedCompany);
    }

    @Override
    public CompanyResponse deleteCompanyById(String id) {
        Company company = getOrThrow(id);
        companyRepository.delete(company);
        return companyMapper.fromEntityToResponse(company);
    }

    @Override
    public User extractUserInfo(Jwt jwt) {
        return User.builder()
                .surname(jwt.getClaim(FAMILY_NAME))
                .name(jwt.getClaim(GIVEN_NAME))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .email(jwt.getClaim(EMAIL))
                .username(jwt.getClaim(USERNAME))
                .build();
    }


    private Company getOrThrow(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND, id)));
    }

    private void checkingCompanyExistByName(String name) {
        if (companyRepository.existsByCompanyName(name)) {
            throw new CompanyAlreadyExistException(String.format(COMPANY_ALREADY_EXIST, name));
        }
    }
}
