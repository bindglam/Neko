package com.bindglam.neko.api.pack.host;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface PackHost {
    void start();

    void end();

    void sendPack(Player player, Component message);
}
