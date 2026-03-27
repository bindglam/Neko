package io.github.bindglam.neko.content;

import io.github.bindglam.neko.registry.Registries;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public interface ContentType<T extends Content> {
    @NotNull String id();

    @NotNull Class<T> clazz();

    boolean load(@NotNull Registries registries, @NotNull ConfigurationSection config);
}
