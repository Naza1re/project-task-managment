package org.example.projectservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.projectservice.client.CompanyFeignClient;
import org.example.projectservice.dto.response.CompanyResponse;
import org.example.projectservice.exception.CompanyNotFoundException;
import org.example.projectservice.exception.NotFoundException;
import org.example.projectservice.exception.ServiceUnAvailableException;
import org.example.projectservice.service.CompanyService;
import org.example.projectservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyFeignClient companyFeignClient;

    @Override
    @CircuitBreaker(name = "company", fallbackMethod = "fallBackMethodCompanyService")
    public CompanyResponse getCompanyById(String companyId) {
        return companyFeignClient.getCompanyById(companyId);
    }

    private CompanyResponse fallBackMethodCompanyService(Exception e) {
        throw new ServiceUnAvailableException(String.format(ExceptionMessages.COMPANY_SERVICE_NOT_AVAILABLE_WITH_MESSAGE, e.getMessage()));
    }
    private CompanyResponse fallBackMethodCompanyService(NotFoundException e) {
        throw new CompanyNotFoundException(String.format(ExceptionMessages.COMPANY_NOT_FOUND_EXCEPTION_WITH_MESSAGE, e.getMessage()));
    }
}
