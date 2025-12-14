package com.bindglam.neko.api.registry;

import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.sound.SoundEvent;
import com.bindglam.neko.api.utils.Factory;
import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.content.item.Item;

public interface BuiltInRegistries {
    Registry<Item> ITEMS = empty();
    Registry<Block> BLOCKS = empty();
    Registry<Factory<BlockRenderer, Block>> BLOCK_RENDERERS = empty();
    Registry<Glyph> GLYPHS = empty();
    Registry<Furniture> FURNITURE = empty();
    Registry<SoundEvent> SOUNDS = empty();

    private static <T> Registry<T> empty() {
        return new ScalableRegistry<>();
    }
}
