package ru.otus.protobuf.model;

public class Range {
    private final long firstValue;
    private final long lastValue;

    public Range(long firstValue, long lastValue) {
        this.firstValue = firstValue;
        this.lastValue = lastValue;
    }

    public long getFirstValue() {
        return firstValue;
    }

    public long getLastValue() {
        return lastValue;
    }
}
