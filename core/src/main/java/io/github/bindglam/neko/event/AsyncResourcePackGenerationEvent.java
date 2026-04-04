package io.github.bindglam.neko.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

public record AsyncResourcePackGenerationEvent(
        @NotNull ResourcePack resourcePack
) implements Event {
    @ApiStatus.Internal
    public AsyncResourcePackGenerationEvent {
    }
}
