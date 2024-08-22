package org.example.companyservice.repository;

import org.example.companyservice.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Boolean existsByCompanyName(String name);
}