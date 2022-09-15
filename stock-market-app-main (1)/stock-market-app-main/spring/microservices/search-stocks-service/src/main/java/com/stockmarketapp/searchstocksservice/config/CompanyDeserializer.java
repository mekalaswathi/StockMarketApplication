package com.stockmarketapp.searchstocksservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarketapp.searchstocksservice.model.Company;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CompanyDeserializer implements Deserializer<Company> {
    @Override public void close() {

    }

    @Override public void configure(Map<String, ?> arg0, boolean arg1) {

    }

    @Override
    public Company deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        Company company = null;
        try {
            company = mapper.readValue(arg1, Company.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return company;
    }
}
