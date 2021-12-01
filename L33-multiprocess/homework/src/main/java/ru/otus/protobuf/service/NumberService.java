package ru.otus.protobuf.service;

import ru.otus.protobuf.model.Range;
import ru.otus.protobuf.model.Value;

import java.util.List;

public interface NumberService {
    List<Value> getSequence(Range range);
}
