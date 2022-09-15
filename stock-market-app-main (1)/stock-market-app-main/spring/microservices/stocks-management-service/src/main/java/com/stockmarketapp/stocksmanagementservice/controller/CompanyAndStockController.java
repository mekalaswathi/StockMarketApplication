package com.stockmarketapp.stocksmanagementservice.controller;

import com.stockmarketapp.stocksmanagementservice.model.Company;
import com.stockmarketapp.stocksmanagementservice.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/market")
public class CompanyAndStockController {
   
	@Autowired

    private static final String ADD_COMPANY_TOPIC = "add_company";
    private static final String ADD_STOCK_TOPIC = "add_stock";
    private static final String DELETE_COMPANY_TOPIC = "delete_company";
    AtomicInteger companyIdIndex = new AtomicInteger();
    AtomicInteger stockIdIndex = new AtomicInteger();

    @Autowired
    KafkaTemplate<String, Company> companyKafkaTemplate;
    @Autowired
    KafkaTemplate<String, Stock> stockKafkaTemplate;
    @Autowired
     KafkaTemplate<String, String> deleteCompanyKafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @CrossOrigin
    @PostMapping("/company/register")
    public ResponseEntity<Company> saveCompany(@RequestBody Company company) {
        //TODO test implementation - To be removed
        Company newCompany = new Company();
        newCompany.setCompanyName("Company1");
        newCompany.setCompanyCode("COMP01");
        newCompany.setCompanyCeo("Test");
        newCompany.setCompanyWebsite("www.comp1.com");
        newCompany.setStockExchange("NSE");
        newCompany.setCompanyTurnover(new BigDecimal(300000000));

        company.set_id(companyIdIndex.incrementAndGet());
        company.setCompanyCode(company.getCompanyName().substring(0, 4) +company.get_id());
        logger.info("Registering a new company with companyCode {}.",newCompany.getCompanyCode());
        companyKafkaTemplate.send(ADD_COMPANY_TOPIC, newCompany);
        companyKafkaTemplate.flush();
        logger.info("Successfully sent to Add Company topic.");
    	return new ResponseEntity<Company>(company,HttpStatus.OK);
    }
    
  
    @CrossOrigin
    @PostMapping("/stock/add/{companyCode}")
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock,@PathVariable String companyCode ) {
        //TODO test implementation - To be removed
    	stock.setCompanyName(companyCode);
    	stock.setStockPrice(new BigDecimal(580));
    	stock.setCompanyCode("COMP01");
        stock.setCreatedAt(new Date());

        stock.set_id(stockIdIndex.incrementAndGet());
        logger.info("Registering a new Stock for a companyCode {}.",companyCode);
        stockKafkaTemplate.send(ADD_STOCK_TOPIC, stock);
        stockKafkaTemplate.flush();
        logger.info("Successfully sent to Add Stock topic.");
        return new ResponseEntity<Stock>(stock,HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/company/delete/{companyCode}")
    public ResponseEntity<?> deleteCompany(@PathVariable String companyCode) {
        logger.info("Deleting a company with companyCode {}.",companyCode);
        deleteCompanyKafkaTemplate.send(DELETE_COMPANY_TOPIC, companyCode);
        deleteCompanyKafkaTemplate.flush();
        logger.info("Successfully sent to Delete Company topic.");
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }
}
