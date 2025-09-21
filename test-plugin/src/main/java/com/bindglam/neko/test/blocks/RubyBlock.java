package com.bindglam.neko.test.blocks;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.ItemStackWrapper;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.List;

public class RubyBlock extends CustomBlock {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_block");

    public RubyBlock() {
        super(KEY,
                CustomItemProperties.builder()
                        .name(Component.text("루비 블록"))
                        .model(new NamespacedKey("defaultassets", "block/ruby_block")),
                CustomBlockProperties.builder()
                        .model(new NamespacedKey("defaultassets", "block/ruby_block"))
                        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(new NamespacedKey("neko", "note_block")))
                        .hardness(5.0f)
                        .correctTools(new CustomBlockProperties.CorrectTools(List.of(Tag.ITEMS_PICKAXES), List.of()))
                        .drops(new CustomBlockProperties.Drops(List.of(new CustomBlockProperties.Drops.DropData(ItemStackWrapper.of(new NamespacedKey("defaultassets", "ruby")), 0, 1f))))
        );
    }
}
