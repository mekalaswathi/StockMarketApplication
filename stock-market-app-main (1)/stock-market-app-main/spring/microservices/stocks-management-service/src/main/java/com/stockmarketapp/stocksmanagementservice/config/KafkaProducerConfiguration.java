package com.stockmarketapp.stocksmanagementservice.config;


import com.stockmarketapp.stocksmanagementservice.model.Company;
import com.stockmarketapp.stocksmanagementservice.model.Stock;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public ProducerFactory<String, Company> addCompanyProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.stockmarketapp.stocksmanagementservice.config.CompanySerializer");

        return new DefaultKafkaProducerFactory<String, Company>(config);
    }

    @Bean
    public KafkaTemplate<String, Company> addCompanyKafkaTemplate() {
        return new KafkaTemplate<>(addCompanyProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Stock> addStockProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.stockmarketapp.stocksmanagementservice.config.StockSerializer");

        return new DefaultKafkaProducerFactory<String, Stock>(config);
    }

    @Bean
    public KafkaTemplate<String, Stock> addStockKafkaTemplate() {
        return new KafkaTemplate<>(addStockProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> deleteCompanyProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<String, String>(config);
    }

    @Bean
    public KafkaTemplate<String, String> deleteCompanyKafkaTemplate() {
        return new KafkaTemplate<>(deleteCompanyProducerFactory());
    }
}
