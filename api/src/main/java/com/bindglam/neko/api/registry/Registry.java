package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface Registry<T> extends Iterable<T> {
    @Nullable T getOrNull(@NotNull Key key);

    default @NotNull T get(@NotNull Key key) {
        return Objects.requireNonNull(getOrNull(key));
    }

    @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet();

    void register(@NotNull Key key, @NotNull T value);

    void clear();

    int size();
}
