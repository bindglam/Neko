package com.bindglam.neko.test;

import com.bindglam.neko.api.content.block.CustomBlock;
import com.bindglam.neko.api.content.item.ItemProperties;
import com.bindglam.neko.api.content.item.block.BlockItem;
import com.bindglam.neko.api.content.item.block.CustomBlockItem;
import com.bindglam.neko.api.event.ContentsLoadEvent;
import com.bindglam.neko.api.event.PackEvent;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import com.bindglam.neko.test.blocks.DeepslateRubyOreBlock;
import com.bindglam.neko.test.blocks.RubyBlock;
import com.bindglam.neko.test.blocks.RubyOreBlock;
import com.bindglam.neko.test.blocks.TestBlock;
import com.bindglam.neko.test.glyphs.RubyGlyph;
import com.bindglam.neko.test.items.RubyItem;
import com.bindglam.neko.test.items.TestItem;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NekoTestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onContentsLoad(ContentsLoadEvent event) {
        BuiltInRegistries.GLYPHS.register(RubyGlyph.KEY, new RubyGlyph());

        BuiltInRegistries.ITEMS.register(TestItem.KEY, new TestItem());
        BuiltInRegistries.ITEMS.register(RubyItem.KEY, new RubyItem());

        registerBlock(TestBlock.KEY, new TestBlock(), Component.text("테스트 블록"));
        registerBlock(RubyBlock.KEY, new RubyBlock(), Component.text("루비 블록"));
        registerBlock(RubyOreBlock.KEY, new RubyOreBlock(), Component.text("루비 광석"));
        registerBlock(DeepslateRubyOreBlock.KEY, new DeepslateRubyOreBlock(), Component.text("심층암 루비 광석"));
    }

    @EventHandler
    public void onPack(PackEvent event) {
        event.addPluginPack(this);
    }

    private static void registerBlock(NamespacedKey key, CustomBlock block, Component name) {
        BuiltInRegistries.ITEMS.register(key, new CustomBlockItem(block.getKey(), ItemProperties.builder().name(name).model(block.properties().model()).build(), block));
        BuiltInRegistries.BLOCKS.register(key, block);
    }
}
