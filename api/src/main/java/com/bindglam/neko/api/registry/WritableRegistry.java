package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;

public interface WritableRegistry<T> extends Registry<T> {
    void freeze();

    void register(Key key, T value);

    void clear();
}
