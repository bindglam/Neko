package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.block.properties.CorrectTools;
import com.bindglam.neko.api.content.block.CustomBlock;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

public class RubyBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_block");

    public RubyBlock() {
        super(KEY,
                BlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/ruby_block"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .hardness(5.0f)
                        .blastResistance(6.0f)
                        .correctTools(CorrectTools.builder().tags(Tag.ITEMS_PICKAXES).build())
                        .build()
        );
    }
}
