package com.bindglam.neko.api.content;

import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public interface ContentLoader {
    void load(Key key, ConfigurationSection config);

    @NotNull String id();
}
