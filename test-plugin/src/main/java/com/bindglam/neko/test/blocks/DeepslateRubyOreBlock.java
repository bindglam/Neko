package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import org.bukkit.NamespacedKey;

public class DeepslateRubyOreBlock extends Block {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "deepslate_ruby_ore");

    public DeepslateRubyOreBlock() {
        super(KEY,
                BlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/deepslate_ruby_ore"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.getOrThrow(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .build()
        );
    }
}
