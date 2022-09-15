package com.stockmarketapp.searchstocksservice.config;

import java.io.Serializable;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomDeserializer<T extends Serializable> implements Deserializer<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String topic, byte[] objectData) {
        return (objectData == null) ? null : (T) SerializationUtils.deserialize(objectData);
    }

    @Override
    public void close() {
    }
}
