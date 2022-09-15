package com.stockmarketapp.searchstocksservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "company")
@Getter
@Setter
@ToString
public class Company {
    @Id
    private int _id;
    @Indexed(unique = true)
    private String companyCode;
    private String companyName;
    private String companyCeo;
    private BigDecimal companyTurnover;
    private String companyWebsite;
    private String stockExchange;
    private List<Stock> stocks;
}
