package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.block.CustomBlock;
import com.bindglam.neko.api.content.block.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import org.bukkit.NamespacedKey;

public class DeepslateRubyOreBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "deepslate_ruby_ore");

    public DeepslateRubyOreBlock() {
        super(KEY,
                BlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/deepslate_ruby_ore"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .build()
        );
    }
}
