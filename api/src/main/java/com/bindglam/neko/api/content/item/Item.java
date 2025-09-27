package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.EventState;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Item extends Keyed {
    default EventState onUse(Player player, @NotNull ItemStack itemStack) {
        return EventState.CONTINUE;
    }

    @NotNull ItemStack itemStack();

    @NotNull ItemProperties itemProperties();

    default boolean isSame(ItemStack other) {
        return itemStack().isSimilar(other);
    }
}
