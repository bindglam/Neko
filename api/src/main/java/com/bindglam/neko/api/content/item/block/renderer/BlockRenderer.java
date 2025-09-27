package com.bindglam.neko.api.content.item.block.renderer;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public interface BlockRenderer {
    NamespacedKey NOTE_BLOCK_RENDERER = new NamespacedKey("neko", "note_block");

    @NotNull BlockState createBlockState();

    default void place(@NotNull Location location) {
        createBlockState().copy(location).update();
    }

    boolean isSame(@NotNull BlockState block);
}
