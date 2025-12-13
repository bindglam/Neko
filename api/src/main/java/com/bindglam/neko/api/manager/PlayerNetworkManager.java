package com.bindglam.neko.api.manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerNetworkManager extends Managerial {
    void inject(@NotNull Player player);

    void eject(@NotNull Player player);
}
