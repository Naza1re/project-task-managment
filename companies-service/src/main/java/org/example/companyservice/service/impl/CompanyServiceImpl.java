package org.example.companyservice.service.impl;

import lombok.RequiredArgsConstructor;
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
    public Company createCompany(Company companyRequest) {
        checkingCompanyExistByName(companyRequest.getCompanyName());

        return companyRepository.save(companyRequest);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(String id) {
        return getOrThrow(id);
    }

    @Override
    public Company updateCompanyById(String id, Company request) {

        Company company = getOrThrow(id);
        company.setId(company.getId());

        return companyRepository.save(company);
    }

    @Override
    public Company deleteCompanyById(String id) {
        Company company = getOrThrow(id);
        companyRepository.delete(company);
        return company;
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
