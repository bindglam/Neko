package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Registry<T> extends Iterable<T> {
    Optional<T> get(@NotNull Key key);

    default @NotNull T getOrThrow(@NotNull Key key) {
        return get(key).orElseThrow();
    }

    @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet();

    void register(@NotNull Key key, @NotNull T value);

    void clear();

    int size();
}
