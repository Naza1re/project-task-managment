package org.example.projectservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectservice.client.CompanyFeignClient;
import org.example.projectservice.dto.response.CompanyResponse;
import org.example.projectservice.exception.NotFoundException;
import org.example.projectservice.exception.ServiceUnAvailableException;
import org.example.projectservice.service.CompanyService;
import org.example.projectservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyFeignClient companyFeignClient;

    @Override
    @CircuitBreaker(name = "company", fallbackMethod = "fall")
    public CompanyResponse getCompanyById(String companyId) {
        log.info("try to get company by id {}", companyId);
        return companyFeignClient.getCompanyById(companyId);
    }

    public CompanyResponse fall(String companyId, Throwable e) {
        log.info("Fallback ServiceUnAvailable method was called due to :{}", e.getMessage());
        throw new ServiceUnAvailableException(ExceptionMessages.COMPANY_SERVICE_NOT_AVAILABLE_WITH_MESSAGE);
    }

    public CompanyResponse fall(String companyId, NotFoundException e) {
        log.info("Fallback NotFound method was called due to :{}", e.getMessage());
        throw new ServiceUnAvailableException(String.format(ExceptionMessages.COMPANY_NOT_FOUND_EXCEPTION_WITH_MESSAGE, e.getMessage()));
    }


}
