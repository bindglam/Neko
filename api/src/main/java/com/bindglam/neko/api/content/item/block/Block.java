package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.Item;
import org.bukkit.Keyed;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Block extends Keyed, Item {
    default EventState onInteract(Player player, org.bukkit.block.Block block) {
        return EventState.CONTINUE;
    }

    boolean isSame(BlockState other);

    BlockState blockState();

    @NotNull BlockProperties blockProperties();
}
