package com.bindglam.neko.api.event;

import com.bindglam.neko.api.content.furniture.Furniture;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class FurnitureBreakEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Furniture furniture;
    private final Location location;

    private boolean cancelled = false;

    @ApiStatus.Internal
    public FurnitureBreakEvent(@NotNull Player player, Furniture furniture, Location location) {
        super(player);
        this.furniture = furniture;
        this.location = location;
    }

    public @NotNull Furniture getFurniture() {
        return furniture;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
