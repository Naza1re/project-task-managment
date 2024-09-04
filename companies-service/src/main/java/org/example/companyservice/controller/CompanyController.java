package org.example.companyservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.companyservice.dto.CompanyListResponse;
import org.example.companyservice.dto.request.CompanyRequest;
import org.example.companyservice.dto.response.CompanyResponse;
import org.example.companyservice.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(companyRequest));
    }

    @GetMapping
    public ResponseEntity<CompanyListResponse> getAllCompany() {
        return ResponseEntity.ok(companyService.getAllCompany());

    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable String id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompanyById(
            @PathVariable String id, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.updateCompanyById(id, companyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyResponse> deleteCompnayById(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(companyService.deleteCompanyById(id));
    }
}
