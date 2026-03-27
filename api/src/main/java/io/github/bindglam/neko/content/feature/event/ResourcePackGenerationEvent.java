package io.github.bindglam.neko.content.feature.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

public record ResourcePackGenerationEvent(
        @NotNull ResourcePack resourcePack
) implements FeatureEvent {
    @ApiStatus.Internal
    public ResourcePackGenerationEvent {
    }
}
