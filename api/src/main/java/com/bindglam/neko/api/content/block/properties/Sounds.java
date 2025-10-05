package com.bindglam.neko.api.content.block.properties;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record Sounds(
        @NotNull Key placeSound,
        @NotNull Key breakSound
) {
}
