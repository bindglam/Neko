package com.bindglam.neko.api.registry;

import net.kyori.adventure.key.Key;

public interface RegistryEvent<T> {
    void register(Key key, T value);

    void clear();
}
