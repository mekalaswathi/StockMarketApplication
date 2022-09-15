package com.stockmarketapp.searchstocksservice.controller;

import com.stockmarketapp.searchstocksservice.exception.CompanyNotFoundException;
import com.stockmarketapp.searchstocksservice.model.Company;
import com.stockmarketapp.searchstocksservice.model.Stock;
import com.stockmarketapp.searchstocksservice.repository.CompanyRepository;
import com.stockmarketapp.searchstocksservice.repository.StockRepository;
import com.stockmarketapp.searchstocksservice.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1.0/market")
public class SearchController {

    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    StockRepository stockRepository;

    private static final String ADD_COMPANY_TOPIC = "add_company";
    private static final String ADD_STOCK_TOPIC = "add_stock";
    private static final String DELETE_COMPANY_TOPIC = "delete_company";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @CrossOrigin
    @GetMapping("/company/getAll")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @CrossOrigin
    @GetMapping("/company/info/{companyCode}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String  companyCode) throws CompanyNotFoundException {
        return companyService.getCompanyById(companyCode);
    }

    @CrossOrigin
    @GetMapping("/stock/get/{companyCode}/{startDate}/{endDate}")
    public ResponseEntity<Company> getStockPricesForCompany(@PathVariable String  companyCode, @PathVariable String  startDate, @PathVariable String  endDate ) throws CompanyNotFoundException, ParseException {
        return companyService.getStockPricesForCompany(companyCode, startDate, endDate);
    }

    @KafkaListener(topics = ADD_COMPANY_TOPIC, groupId="group_id", containerFactory = "companyKafkaListenerFactory")
    public void consumeJson(Company company) {
        logger.info("Request received for registering a new company with companyCode {}",company.getCompanyCode());
        companyRepository.save(company);
        logger.info("Company registered successfully.");
    }

    @KafkaListener(topics = ADD_STOCK_TOPIC, groupId="group_id", containerFactory = "stockKafkaListenerFactory")
    public void consumeJson(Stock stock) {
        logger.info("Request received for adding a new stock");
        stockRepository.save(stock);
        logger.info("Stock added successfully.");
    }

    @KafkaListener(topics = DELETE_COMPANY_TOPIC, groupId="group_id", containerFactory = "deleteCompanyKafkaListenerFactory")
    public void consumeJson(String companyCode) {
        logger.info("Request received for deleting a company with companyCode {}",companyCode);
        companyService.deleteCompanyByCode(companyCode);
        logger.info("Company and corresponding stocks deleted successfully.");
    }
}
