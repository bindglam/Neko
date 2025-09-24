package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

public class DeepslateRubyOreBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "deepslate_ruby_ore");

    public DeepslateRubyOreBlock() {
        super(KEY,
                CustomItemProperties.builder()
                        .name(Component.text("심층암 루비 광석"))
                        .model(new NamespacedKey("defaultassets", "block/deepslate_ruby_ore"))
                        .build(),
                CustomBlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/deepslate_ruby_ore"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .build()
        );
    }
}
