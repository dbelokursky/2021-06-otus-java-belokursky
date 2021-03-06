package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    Logger log = LoggerFactory.getLogger(MyCache.class);

    private final Map<K, V> cache;

    private final List<HwListener<K, V>> listeners;

    public MyCache() {
        this.cache = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyAllListeners(CacheActionEnum.ADD, key, value);
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        if (value != null) {
            notifyAllListeners(CacheActionEnum.DELETE, key, value);
        }
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            notifyAllListeners(CacheActionEnum.READ, key, value);
        }
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    @Override
    public long size() {
        return cache.size();
    }

    private void notifyAllListeners(CacheActionEnum action, K key, V value) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action.getDescription());
            } catch (Exception e) {
                log.error("Listener notification error");
            }
        });
    }
}
