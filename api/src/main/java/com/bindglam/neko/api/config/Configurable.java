package com.bindglam.neko.api.config;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Configurable<T, C> {
    Configurable<Component, String> COMPONENT = new ComponentConfigurable();
    Configurable<List<Component>, List<String>> COMPONENT_LIST = new ComponentListConfigurable();
    Configurable<Key, String> KEY = new KeyConfigurable();

    @Nullable T load(@Nullable C config);
}
