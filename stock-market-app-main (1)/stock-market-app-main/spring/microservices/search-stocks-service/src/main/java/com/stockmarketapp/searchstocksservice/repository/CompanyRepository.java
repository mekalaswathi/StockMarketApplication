package com.stockmarketapp.searchstocksservice.repository;

import com.stockmarketapp.searchstocksservice.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
    Company deleteCompanyByCompanyCode(String s);
    Company findCompanyByCompanyCode(String s);
}
