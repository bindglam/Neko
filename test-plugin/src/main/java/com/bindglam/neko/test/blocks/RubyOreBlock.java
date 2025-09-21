package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

public class RubyOreBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_ore");

    public RubyOreBlock() {
        super(KEY,
                CustomItemProperties.builder()
                        .name(Component.text("루비 광석"))
                        .model(new NamespacedKey("defaultassets", "block/ruby_ore")),
                CustomBlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/ruby_ore"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(new NamespacedKey("neko", "note_block")))
        );
    }
}
