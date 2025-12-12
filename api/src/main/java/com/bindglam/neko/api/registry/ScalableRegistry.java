package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

@ApiStatus.Internal
public class ScalableRegistry<T> implements Registry<T> {
    private final Map<Key, T> map = new HashMap<>();

    @Override
    public Optional<T> get(@NotNull Key key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet() {
        return map.entrySet();
    }

    @Override
    public synchronized void register(@NotNull Key key, @NotNull T value) {
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
