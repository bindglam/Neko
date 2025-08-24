package com.bindglam.neko.api.config;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KeyConfigurable implements Configurable<Key, String> {
    @Override
    public @Nullable Key load(@KeyPattern @Nullable String value) {
        if(value == null) return null;

        return Key.key(value);
    }
}
