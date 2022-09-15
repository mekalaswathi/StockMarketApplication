package com.stockmarketapp.searchstocksservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableCaching
@EnableMongoRepositories
public class SearchStocksServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchStocksServiceApplication.class, args);
	}

}
