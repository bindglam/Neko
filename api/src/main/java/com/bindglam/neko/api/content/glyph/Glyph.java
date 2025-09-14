package com.bindglam.neko.api.content.glyph;

import com.bindglam.neko.api.NekoProvider;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Glyph implements Keyed {
    public static final NamespacedKey SHIFT_GLYPH_KEY = new NamespacedKey("neko", "shift");

    private final NamespacedKey key;
    private final GlyphProperties properties;

    private final NamespacedKey fontKey;

    private char character;

    public Glyph(NamespacedKey key, GlyphProperties properties) {
        this.key = key;
        this.properties = properties;

        this.fontKey = new NamespacedKey(key.getNamespace(), "default");

        loadData();
    }

    private void loadData() {
        File cacheFile = NekoProvider.neko().cacheManager().getCache("glyphs.yml");
        if(cacheFile == null) {
            NekoProvider.neko().cacheManager().saveCache("glyphs.yml", (file) -> {});
            cacheFile = Objects.requireNonNull(NekoProvider.neko().cacheManager().getCache("glyphs.yml"));
        }

        YamlConfiguration glyphCache = YamlConfiguration.loadConfiguration(cacheFile);

        if(glyphCache.get(key.toString() + ".char") == null) {
            character = glyphCache.getString("glyph.next-char", "a").charAt(0);

            glyphCache.set(key + ".char", character);

            glyphCache.set("glyph.next-char", character+1);
        } else {
            character = glyphCache.getString(key + ".char", "a").charAt(0);
        }

        NekoProvider.neko().cacheManager().saveCache("glyphs.yml", (file) -> {
            try {
                glyphCache.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @NotNull
    public GlyphProperties properties() {
        return properties;
    }

    @ApiStatus.Internal
    public char character() {
        return character;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @NotNull
    public Component component(@NotNull GlyphBuilder builder) {
        return BuiltInRegistries.GLYPHS.get(Glyph.SHIFT_GLYPH_KEY).component(builder).append(Component.text(character).font(fontKey));
    }
}
