package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import org.bukkit.Keyed;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Block extends Keyed {
    @NotNull BlockState blockState();

    boolean isSame(BlockState other);

    @NotNull BlockProperties properties();

    default @Nullable Item item() {
        return BuiltInRegistries.ITEMS.getOrNull(getKey());
    }
}
