package org.example.management.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.SerializationFileException;
import org.example.model.GarageSlot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GarageSlotSerialization {
    private static final String PATH = "src/main/resources/list_garage_slots.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private File file;


    public void writeList(List<GarageSlot> listForSerialization) {
        try {
            mapper.writeValue(file, listForSerialization);
        } catch (IOException e) {
            throw new SerializationFileException(e.getMessage());
        }
    }

    public List<GarageSlot> readList() {
        file = new File(PATH);
        try {
            if (file.exists() && file.length() > 0) {
                return mapper.readValue(file, new TypeReference<>() {
                });
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new SerializationFileException(e.getMessage());
        }
    }
}
