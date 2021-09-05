package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ObjectForMessage implements Cloneable {

    private List<String> data;

    public ObjectForMessage() {
        data = new ArrayList<>();
    }

    @Override
    public ObjectForMessage clone() {
        return data != null ? new ObjectForMessage(List.copyOf(data)) : new ObjectForMessage();
    }
}
