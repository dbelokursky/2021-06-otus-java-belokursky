package ru.otus.cachehw;

public enum CacheActionEnum {
    READ(0, "Read from cache"),
    ADD(1, "Add to cache"),
    DELETE(2, "Delete from cache");

    private final int code;
    private final String description;

    CacheActionEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
