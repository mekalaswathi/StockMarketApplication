package com.stockmarketapp.stocksmanagementservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarketapp.stocksmanagementservice.model.Company;
import com.stockmarketapp.stocksmanagementservice.model.Stock;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class StockSerializer implements Serializer<Stock> {

    @Override public void configure(Map<String, ?> map, boolean b) {

    }

    @Override public byte[] serialize(String arg0, Stock arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override public void close() {

    }

}
