package com.bindglam.neko.api.registry;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.sound.SoundEvent;
import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.key.Key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class BuiltInRegistries {
    public static final WritableRegistry<Item> ITEMS = writable();
    public static final WritableRegistry<Block> BLOCKS = writable();
    public static final WritableRegistry<Factory<BlockRenderer, Block>> BLOCK_RENDERERS = writable();
    public static final WritableRegistry<Glyph> GLYPHS = writable();
    public static final WritableRegistry<Furniture> FURNITURE = writable();
    public static final WritableRegistry<SoundEvent> SOUNDS = writable();

    private BuiltInRegistries() {
    }

    private static <T> Registry<T> of(Map<Key, T> preset) {
        return new ReadOnlyRegistry<>(preset);
    }

    @SafeVarargs
    private static <T> Registry<T> of(Pair<Key, T>... preset) {
        return new ReadOnlyRegistry<>(Arrays.stream(preset).collect(HashMap::new, (map, entry) -> map.put(entry.key(), entry.value()), HashMap::putAll));
    }

    private static <T> WritableRegistry<T> writable() {
        return new ScalableRegistry<>();
    }
}
