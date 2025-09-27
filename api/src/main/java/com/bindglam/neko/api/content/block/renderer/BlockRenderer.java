package com.bindglam.neko.api.content.block.renderer;

import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public interface BlockRenderer {
    NamespacedKey NOTE_BLOCK_RENDERER = new NamespacedKey("neko", "note_block");

    @NotNull BlockState createBlockState();

    boolean isSame(@NotNull BlockState block);
}
