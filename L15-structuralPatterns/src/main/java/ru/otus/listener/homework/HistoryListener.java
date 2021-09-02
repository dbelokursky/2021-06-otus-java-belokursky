package ru.otus.listener.homework;

import lombok.RequiredArgsConstructor;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

@RequiredArgsConstructor
public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, List<Memento>> history = new HashMap<>();
    private final DateTimeProvider dateTimeProvider;

    @Override
    public void onUpdated(Message msg) {
        Memento memento = new Memento(msg, dateTimeProvider.getDateTime());
        if (history.putIfAbsent(msg.getId(), new ArrayList<>(List.of(memento))) != null) {
            history.get(msg.getId()).add(memento);
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id))
                .map(mementos -> mementos.get(0))
                .map(Memento::getMessage);
    }
}
