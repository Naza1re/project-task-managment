package org.example.projectservice.client;

import org.example.projectservice.config.FeignClientConfiguration;
import org.example.projectservice.dto.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${service.company.name}",
        path = "${service.company.path}",
        configuration = FeignClientConfiguration.class)
public interface CompanyFeignClient {
    @GetMapping("/{companyId}")
    CompanyResponse getCompanyById(@PathVariable String companyId);

}
