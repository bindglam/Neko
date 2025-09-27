package com.bindglam.neko.api.registry;

import com.bindglam.neko.api.content.Factory;
import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.content.item.Item;

public interface BuiltInRegistries {
    Registry<Item> ITEMS = empty();
    Registry<Block> BLOCKS = empty();
    Registry<Factory<BlockRenderer, Block>> BLOCK_RENDERERS = empty();
    Registry<Glyph> GLYPHS = empty();

    private static <T> Registry<T> empty() {
        return new ScalableRegistry<>();
    }
}
