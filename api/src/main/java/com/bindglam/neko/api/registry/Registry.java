package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public interface Registry<T> extends Iterable<T> {
    @Nullable
    T getOrNull(Key key);

    default @NotNull T get(Key key) {
        return Objects.requireNonNull(getOrNull(key));
    }

    @NotNull
    @Unmodifiable
    Set<Map.Entry<Key, T>> entrySet();

    void lock(Consumer<RegistryEvent<T>> consumer);
}
