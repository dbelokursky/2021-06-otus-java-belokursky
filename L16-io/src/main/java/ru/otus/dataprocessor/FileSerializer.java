package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        ObjectMapper mapper = new ObjectMapper();
        try (var fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            mapper.writeValue(fileOutputStream, data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
