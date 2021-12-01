package ru.otus.protobuf.model;

public class Value {
    private final long currentValue;

    public Value(long currentValue) {
        this.currentValue = currentValue;
    }

    public long getCurrentValue() {
        return currentValue;
    }
}
