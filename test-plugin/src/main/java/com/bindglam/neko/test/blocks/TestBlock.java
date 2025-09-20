package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemType;

import java.util.List;

public class TestBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "testblock");

    public TestBlock() {
        super(KEY,
                CustomItemProperties.builder().type(ItemType.PAPER)
                        .name(Component.text("테스트 아이템"))
                        .lore(List.of(Component.text("테스트 아이템이다.")))
                        .model(new NamespacedKey("defaultassets", "block/testblock")),
                CustomBlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/testblock"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(new NamespacedKey("neko", "note_block")))
        );
    }
}
