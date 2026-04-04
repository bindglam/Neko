package io.github.bindglam.neko.content.feature.event;

import io.github.bindglam.neko.platform.PlatformItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public record ItemStackGenerationEvent(
        @NotNull PlatformItemStack itemStack
) implements FeatureEvent {
    @ApiStatus.Internal
    public ItemStackGenerationEvent {
    }
}
