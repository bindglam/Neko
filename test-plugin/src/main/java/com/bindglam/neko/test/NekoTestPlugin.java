package com.bindglam.neko.test;

import com.bindglam.neko.api.content.item.block.CustomBlock;
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

        registerBlock(TestBlock.KEY, new TestBlock());
        registerBlock(RubyBlock.KEY, new RubyBlock());
        registerBlock(RubyOreBlock.KEY, new RubyOreBlock());
        registerBlock(DeepslateRubyOreBlock.KEY, new DeepslateRubyOreBlock());
    }

    @EventHandler
    public void onPack(PackEvent event) {
        event.addPluginPack(this);
    }

    private static void registerBlock(NamespacedKey key, CustomBlock block) {
        BuiltInRegistries.ITEMS.register(key, block);
        BuiltInRegistries.BLOCKS.register(key, block);
    }
}
