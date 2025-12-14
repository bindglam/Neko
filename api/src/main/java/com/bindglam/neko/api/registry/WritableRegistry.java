package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface WritableRegistry<T> extends Registry<T> {
    void register(@NotNull Key key, @NotNull T value);

    void clear();
}
