package ru.otus.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class MapToMeasurementArraySerializer extends JsonSerializer<Map<?, ?>> {

    @Override
    public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            gen.writeStartObject();
            gen.writeObjectField("name", entry.getKey());
            gen.writeObjectField("value", entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
