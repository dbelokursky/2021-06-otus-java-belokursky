package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.listener.homework.Copyable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ObjectForMessage implements Copyable<ObjectForMessage> {

    private List<String> data;

    public ObjectForMessage() {
        data = new ArrayList<>();
    }

    @Override
    public ObjectForMessage copy() {
        return data != null ? new ObjectForMessage(List.copyOf(data)) : new ObjectForMessage();
    }
}
