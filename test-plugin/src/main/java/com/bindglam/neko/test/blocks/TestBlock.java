package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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

    @Override
    public EventState onInteract(Player player, Block block) {
        player.sendMessage(Component.text("씨봉방아"));
        return EventState.CANCEL;
    }
}
