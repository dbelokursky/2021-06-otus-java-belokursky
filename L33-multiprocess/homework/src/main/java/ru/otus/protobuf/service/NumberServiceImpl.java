package ru.otus.protobuf.service;

import ru.otus.protobuf.model.Range;
import ru.otus.protobuf.model.Value;

import java.util.ArrayList;
import java.util.List;

public class NumberServiceImpl implements NumberService {

    @Override
    public List<Value> getSequence(Range range) {
        List<Value> values = new ArrayList<>();
        for (long i = range.getFirstValue(); i <= range.getLastValue(); i++) {
            values.add(new Value(i));
        }
        return values;
    }
}
