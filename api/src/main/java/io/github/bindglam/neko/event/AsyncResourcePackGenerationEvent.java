package io.github.bindglam.neko.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

@Getter
public class AsyncResourcePackGenerationEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ResourcePack resourcePack;

    public AsyncResourcePackGenerationEvent(ResourcePack resourcePack) {
        super(true);
        this.resourcePack = resourcePack;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
