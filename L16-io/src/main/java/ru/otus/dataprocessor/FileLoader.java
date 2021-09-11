package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import ru.otus.mixin.MeasurementForDeserialization;
import ru.otus.model.Measurement;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> result;
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        try (var fileInputStream = new BufferedInputStream(new FileInputStream(filePath))) {

            ObjectMapper objectMapper = JsonMapper.builder()
                    .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
                    .addMixIn(Measurement.class, MeasurementForDeserialization.class)
                    .build();

            result = objectMapper.readValue(fileInputStream, new TypeReference<>() {
            });

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
