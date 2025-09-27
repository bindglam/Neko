package com.bindglam.neko.api.nms;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public interface NMSHook {
    @NotNull PlayerChannelHandler createChannelHandler(Player player);

    <T> @Nullable T registerForNMS(T value);
}
