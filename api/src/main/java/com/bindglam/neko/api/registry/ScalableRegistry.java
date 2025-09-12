package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@ApiStatus.Internal
public class ScalableRegistry<T> implements Registry<T> {
    private final Map<Key, T> map = new HashMap<>();


    @Override
    public @Nullable T getOrNull(Key key) {
        return map.get(key);
    }

    @Override
    public @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet() {
        return map.entrySet();
    }

    @Override
    public synchronized void register(Key key, T value) {
        map.put(key, value);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }
}
