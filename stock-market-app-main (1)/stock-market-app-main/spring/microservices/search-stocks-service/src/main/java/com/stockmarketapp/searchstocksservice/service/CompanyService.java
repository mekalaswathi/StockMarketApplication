package com.stockmarketapp.searchstocksservice.service;

import com.stockmarketapp.searchstocksservice.exception.CompanyNotFoundException;
import com.stockmarketapp.searchstocksservice.model.Company;
import com.stockmarketapp.searchstocksservice.model.Stock;
import com.stockmarketapp.searchstocksservice.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.stockmarketapp.searchstocksservice.repository.CompanyRepository;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    StockRepository stockRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Cacheable("companies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Cacheable("company")
    public ResponseEntity<Company> getCompanyById(String id) throws CompanyNotFoundException {
        logger.info("Fetching details for company : {}",id);
        Company company = companyRepository.findCompanyByCompanyCode(id);
        if (null != company) {
            List<Stock> stocksForCompany = stockRepository.getStockByCompanyCode(id);
            if (CollectionUtils.isEmpty(stocksForCompany)) {
                logger.info("Currently no stocks found for company: {}",id);
            }
            company.setStocks(stocksForCompany);
            return new ResponseEntity<Company>(company, HttpStatus.OK);
        } else {
            logger.error("Company not found with id: {}" ,id);
            throw new CompanyNotFoundException("Company not found with id: "+id);

        }
    }

    @Cacheable("stocks")
    public ResponseEntity<Company> getStockPricesForCompany(String companyCode, String startDate, String endDate) throws CompanyNotFoundException, ParseException {
        logger.info("Fetching stocks for company : {}",companyCode);
        Company company = companyRepository.findCompanyByCompanyCode(companyCode);
        if (null != company) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<Stock> stocksForCompany = stockRepository.getStocksForCompany(simpleDateFormat.parse(startDate), simpleDateFormat.parse(endDate), companyCode);
            if (CollectionUtils.isEmpty(stocksForCompany)) {
                logger.info("Currently no stocks found for company: {}",companyCode);
            }
            company.setStocks(stocksForCompany);
            return new ResponseEntity<Company>(company, HttpStatus.OK);
        } else {
            logger.error("Company not found with id: {}" ,companyCode);
            throw new CompanyNotFoundException("Company not found with id: "+companyCode);

        }
    }

    @Caching(evict = {
            @CacheEvict(value="company", allEntries=true),
            @CacheEvict(value="companies", allEntries=true),
            @CacheEvict(value="stocks", allEntries=true)})
    public boolean deleteCompanyByCode(String companyCode) {
        companyRepository.deleteCompanyByCompanyCode(companyCode);
        stockRepository.deleteAllStockByCompanyCode(companyCode);
        return true;
    }
}
