package io.github.bindglam.neko.event;

import io.github.bindglam.neko.manager.RegistryManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public record RegistryInitializeEvent(
        @NotNull RegistryManager.GlobalRegistries registries
) implements Event {
    @ApiStatus.Internal
    public RegistryInitializeEvent {
    }
}
