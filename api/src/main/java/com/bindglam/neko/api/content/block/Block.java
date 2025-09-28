package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Block extends Keyed, Translatable {
    default EventState onInteract(Player player, org.bukkit.block.Block block) {
        return EventState.CONTINUE;
    }

    @NotNull BlockState blockState();

    boolean isSame(BlockState other);

    @NotNull BlockProperties properties();

    default @Nullable Item item() {
        return BuiltInRegistries.ITEMS.getOrNull(getKey());
    }

    @Override
    default @NotNull String translationKey() {
        return "block." + getKey().getNamespace() + "." + getKey().getKey();
    }
}
