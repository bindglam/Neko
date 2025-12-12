package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.EventContainer;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.ItemLike;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Block extends Keyed, ItemLike, Translatable, EventContainer {
    @NotNull BlockState blockState();

    boolean isSame(BlockState other);

    @NotNull BlockProperties properties();

    @Override
    default @Nullable Item asItem() {
        return BuiltInRegistries.ITEMS.get(getKey()).orElse(null);
    }

    @Override
    default @NotNull String translationKey() {
        return "block." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
