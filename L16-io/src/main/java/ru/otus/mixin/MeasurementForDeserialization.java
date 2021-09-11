package ru.otus.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.model.Measurement;

public class MeasurementForDeserialization extends Measurement {
    @JsonCreator
    public MeasurementForDeserialization(@JsonProperty("name") String name, @JsonProperty("value") double value) {
        super(name, value);
    }
}
