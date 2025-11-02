package com.bindglam.neko.api.content.glyph;

import com.bindglam.neko.api.NekoProvider;
import com.bindglam.neko.api.content.glyph.properties.GlyphProperties;
import com.bindglam.neko.api.data.Cache;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.font.FontData;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import com.bindglam.neko.api.utils.GsonUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.ShadowColor;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Glyph implements Keyed, Packable {
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
        Cache cache = NekoProvider.neko().cacheManager().getCache("glyphs");

        if(cache.get(key.toString() + ".char") == null) {
            character = cache.getCharacter("glyph.next-char", 'a');

            cache.set(key + ".char", character);

            cache.set("glyph.next-char", character+1);
        } else {
            character = cache.getCharacter(key + ".char", 'a');
        }
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        String path = "assets/" + key.namespace() + "/font/default.json";

        PackFile fontFile = zipper.file(path);

        FontData data;
        if(fontFile != null)
            data = GsonUtils.GSON.fromJson(new String(fontFile.bytes()), FontData.class);
        else
            data = new FontData(new ArrayList<>());

        data.providers().add(new FontData.Bitmap(properties.texture().asString() + ".png", properties.scale(), properties.offsetY(), List.of(character)));

        zipper.addComponent(path, data);
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
        int shadow = 255;
        if(!builder.shadow())
            shadow = 0;

        return BuiltInRegistries.GLYPHS.get(Glyph.SHIFT_GLYPH_KEY).component(builder).append(Component.text(character).font(fontKey).shadowColor(ShadowColor.shadowColor(0, 0, 0, shadow)));
    }
}
