package com.bindglam.neko.api.content.glyph;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record GlyphProperties(
        @NotNull Key texture,
        int offsetY,
        int scale
) {
}
