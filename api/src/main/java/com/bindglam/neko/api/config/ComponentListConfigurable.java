package com.bindglam.neko.api.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentListConfigurable implements Configurable<List<Component>, List<String>> {
    @Override
    public @Nullable List<Component> load(@Nullable List<String> values) {
        if(values == null) return null;

        return values.stream().map(MiniMessage.miniMessage()::deserialize).toList();
    }
}
