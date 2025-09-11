package com.bindglam.neko.api.content.glyph;

import com.bindglam.neko.api.pack.Packable;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Glyph extends Keyed, Packable {
    Key SHIFT_GLYPH_KEY = Key.key("neko", "shift");

    @NotNull GlyphProperties properties();

    @NotNull Component component(@NotNull GlyphBuilder builder);
}
