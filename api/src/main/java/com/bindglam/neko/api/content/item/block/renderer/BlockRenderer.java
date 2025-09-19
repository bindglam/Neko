package com.bindglam.neko.api.content.item.block.renderer;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public interface BlockRenderer {
    void place(@NotNull Location location);

    boolean isSame(@NotNull BlockState block);
}
