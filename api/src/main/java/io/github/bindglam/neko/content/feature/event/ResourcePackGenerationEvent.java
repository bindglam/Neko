package io.github.bindglam.neko.content.feature.event;

import io.github.bindglam.neko.event.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

public record ResourcePackGenerationEvent(
        @NotNull ResourcePack resourcePack
) implements Event {
    @ApiStatus.Internal
    public ResourcePackGenerationEvent {
    }
}
