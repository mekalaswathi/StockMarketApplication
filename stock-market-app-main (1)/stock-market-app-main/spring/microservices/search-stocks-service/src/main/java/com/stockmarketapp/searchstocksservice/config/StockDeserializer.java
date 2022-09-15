package com.stockmarketapp.searchstocksservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarketapp.searchstocksservice.model.Stock;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class StockDeserializer implements Deserializer<Stock> {
    @Override public void close() {

    }

    @Override public void configure(Map<String, ?> arg0, boolean arg1) {

    }

    @Override
    public Stock deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        Stock stock = null;
        try {
            stock = mapper.readValue(arg1, Stock.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return stock;
    }
}
