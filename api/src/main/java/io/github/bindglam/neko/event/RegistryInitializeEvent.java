package io.github.bindglam.neko.event;

import io.github.bindglam.neko.registry.Registries;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Getter
public class RegistryInitializeEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Registries registries;

    @ApiStatus.Internal
    public RegistryInitializeEvent(Registries registries) {
        this.registries = registries;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
