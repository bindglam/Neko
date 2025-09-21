package com.bindglam.neko.test.glyphs;

import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.glyph.GlyphProperties;
import org.bukkit.NamespacedKey;

public class RubyGlyph extends Glyph {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_glyph");

    public RubyGlyph() {
        super(KEY, GlyphProperties.builder()
                .texture(new NamespacedKey("defaultassets", "font/ruby")).offsetY(8).scale(16).build());
    }
}
