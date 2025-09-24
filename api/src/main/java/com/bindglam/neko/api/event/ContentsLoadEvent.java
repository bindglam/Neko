package com.bindglam.neko.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ContentsLoadEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @ApiStatus.Internal
    public ContentsLoadEvent() {
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
