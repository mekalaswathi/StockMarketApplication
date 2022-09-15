package com.stockmarketapp.searchstocksservice.repository;

import com.stockmarketapp.searchstocksservice.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface StockRepository extends MongoRepository<Stock, String> {

    @Query("{'createdAt' : { $gte: ?0, $lte: ?1 }, 'companyCode' : ?2 }")
    public List<Stock> getStocksForCompany(Date from, Date to,String companyCode);

    List<Stock> getStockByCompanyCode(String companyCode);
    Stock deleteAllStockByCompanyCode(String s);

}
