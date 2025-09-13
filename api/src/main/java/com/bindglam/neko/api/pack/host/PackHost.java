package com.bindglam.neko.api.pack.host;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PackHost {
    void start(@NotNull ConfigurationSection config);

    void end();

    void sendPack(@NotNull Player player, @NotNull Component message);

    @NotNull String id();
}
