package io.github.bindglam.neko.resourcepack;

import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

public record PackGenerationContext(
        @NotNull ResourcePack pack
) {
}
