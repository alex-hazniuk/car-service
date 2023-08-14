package org.example.repository.FileRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.SerializationFileException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CarServiceStoreHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Path path;

    public CarServiceStoreHandler(Path path) {
        this.path = path;
    }

    public void write(State state) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, state);
        } catch (IOException e) {
            System.out.println(e);
            throw new SerializationFileException(e.getMessage());
        }
    }

    public State read() {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return mapper.readValue(reader, State.class);
        } catch (IOException e) {
            return new State();
        }
    }
}
