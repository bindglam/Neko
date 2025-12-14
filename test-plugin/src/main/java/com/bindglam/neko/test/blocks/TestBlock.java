package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.EventState;
import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class TestBlock extends Block {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "testblock");

    public TestBlock() {
        super(KEY,
                BlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/testblock"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.getOrThrow(BlockRenderer.NOTE_BLOCK_RENDERER))
                        .build(),
                new EventHandler()
        );
    }

    private static final class EventHandler implements com.bindglam.neko.api.content.EventHandler {
        @Override
        public EventState onInteract(Player player, org.bukkit.block.Block block) {
            player.sendMessage(Component.text("씨봉방아"));
            return EventState.CANCEL;
        }
    }
}
