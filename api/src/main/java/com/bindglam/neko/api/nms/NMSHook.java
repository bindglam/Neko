package com.bindglam.neko.api.nms;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface NMSHook {
    @NotNull PlayerChannelHandler createChannelHandler(Player player);
}
