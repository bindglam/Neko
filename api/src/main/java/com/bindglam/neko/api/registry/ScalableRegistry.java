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
import java.util.function.Consumer;

@ApiStatus.Internal
public class ScalableRegistry<T> implements Registry<T> {
    private final Map<Key, T> map = new HashMap<>();

    private boolean isLocked = false;

    @Override
    public @Nullable T getOrNull(Key key) {
        if(isLocked)
            throw new IllegalStateException("Locked");

        return map.get(key);
    }

    @Override
    public @NotNull @Unmodifiable Set<Map.Entry<Key, T>> entrySet() {
        if(isLocked)
            throw new IllegalStateException("Locked");

        return map.entrySet();
    }

    @Override
    public void lock(Consumer<RegistryEvent<T>> consumer) {
        if(isLocked)
            throw new IllegalStateException("Locked");

        isLocked = true;

        consumer.accept(new RegistryEvent<>() {
            @Override
            public void register(Key key, T value) {
                map.put(key, value);
            }
        });

        isLocked = false;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return map.values().iterator();
    }
}
