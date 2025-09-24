package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

import java.util.List;

public class TestBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "testblock");

    public TestBlock() {
        super(KEY,
                CustomItemProperties.builder()
                        .name(Component.text("테스트 아이템"))
                        .lore(List.of(Component.text("테스트 아이템이다.")))
                        .model(new NamespacedKey("defaultassets", "block/testblock"))
                        .build(),
                CustomBlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/testblock"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .build()
        );
    }
}
