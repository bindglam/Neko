package io.github.bindglam.neko.content.feature.event;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public record ItemStackGenerationEvent(
        @NotNull ItemStack itemStack
) implements FeatureEvent {
    @ApiStatus.Internal
    public ItemStackGenerationEvent {
    }
}
