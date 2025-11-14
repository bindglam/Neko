package com.bindglam.neko.api.content;

import com.bindglam.neko.api.content.furniture.FurnitureDisplay;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface EventHandler {
    EventHandler EMPTY = new EventHandler() {};

    default EventState onUse(Player player, @NotNull ItemStack itemStack) {
        return EventState.CONTINUE;
    }

    default EventState onInteract(Player player, org.bukkit.block.Block block) {
        return EventState.CONTINUE;
    }

    default EventState onInteract(Player player, Location location, FurnitureDisplay display) {
        return EventState.CONTINUE;
    }
}
