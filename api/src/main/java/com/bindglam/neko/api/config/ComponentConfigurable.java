package com.bindglam.neko.api.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.Nullable;

public class ComponentConfigurable implements Configurable<Component, String> {
    @Override
    public @Nullable Component load(@Nullable String value) {
        if(value == null) return null;

        return MiniMessage.miniMessage().deserialize(value);
    }
}
