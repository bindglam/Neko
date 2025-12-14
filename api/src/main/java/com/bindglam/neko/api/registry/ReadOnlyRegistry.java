package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

@ApiStatus.Internal
public final class ReadOnlyRegistry<T> implements Registry<T> {
    private final Map<Key, T> map;

    ReadOnlyRegistry(Map<Key, T> preset) {
        this.map = Map.copyOf(preset);
    }

    @Override
    public Optional<T> get(@NotNull Key key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet() {
        return map.entrySet();
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
