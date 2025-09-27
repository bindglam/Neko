package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.ItemProperties;
import com.bindglam.neko.api.content.item.ItemStackWrapper;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.BlockProperties;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.List;

public class RubyBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_block");

    public RubyBlock() {
        super(KEY,
                ItemProperties.builder()
                        .name(Component.text("루비 블록"))
                        .model(new NamespacedKey("defaultassets", "block/ruby_block"))
                        .build(),
                BlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/ruby_block"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .hardness(5.0f)
                        .correctTools(new BlockProperties.CorrectTools(List.of(Tag.ITEMS_PICKAXES), List.of()))
                        .drops(new BlockProperties.Drops(List.of(new BlockProperties.Drops.DropData(ItemStackWrapper.of(new NamespacedKey("defaultassets", "ruby")), 0, 1f))))
                        .build()
        );
    }
}
