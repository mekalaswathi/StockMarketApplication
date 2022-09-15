package com.stockmarketapp.searchstocksservice.service;

import com.stockmarketapp.searchstocksservice.exception.StockNotFoundException;
import com.stockmarketapp.searchstocksservice.model.Stock;
import com.stockmarketapp.searchstocksservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Cacheable("stocks")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Cacheable("stock")
    public ResponseEntity<Stock> getStockById(String id) throws StockNotFoundException {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isPresent()) {
            return new ResponseEntity<Stock>(stock.get(), HttpStatus.OK);
        } else {
            System.out.println("Stock not found with id: " + id);
            throw new StockNotFoundException("Stock not found with id: "+id);

        }
    }
}
