package org.example.management.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.SerializationFileException;
import org.example.model.Order;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrderSerialization {
    private static final String PATH = "src/main/resources/list_orders.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private File file;


    public void writeList(Map<Long, Order> listForSerialization) {
        try {
            mapper.writeValue(file, listForSerialization);
        } catch (IOException e) {
            throw new SerializationFileException(e.getMessage());
        }
    }

    public Map<Long, Order> readList() {
        file = new File(PATH);
        try {
            if(file.exists() && file.length() > 0) {
                return mapper.readValue(file, new TypeReference<>() {
                });
            } else {
                return new HashMap<>();
            }
        } catch (IOException e) {
            throw new SerializationFileException(e.getMessage());
        }
    }
}
