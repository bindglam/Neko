package com.bindglam.neko.test;

import com.bindglam.neko.api.content.block.CustomBlock;
import com.bindglam.neko.api.content.block.populator.CustomBlockPopulator;
import com.bindglam.neko.api.content.block.populator.PopulatorSettings;
import com.bindglam.neko.api.content.furniture.CustomFurniture;
import com.bindglam.neko.api.content.item.furniture.CustomFurnitureItem;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlockItem;
import com.bindglam.neko.api.event.ContentsLoadEvent;
import com.bindglam.neko.api.event.GenerateResourcePackEvent;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import com.bindglam.neko.test.blocks.DeepslateRubyOreBlock;
import com.bindglam.neko.test.blocks.RubyBlock;
import com.bindglam.neko.test.blocks.RubyOreBlock;
import com.bindglam.neko.test.blocks.TestBlock;
import com.bindglam.neko.test.furniture.TestFurniture;
import com.bindglam.neko.test.glyphs.RubyGlyph;
import com.bindglam.neko.test.items.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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

        BuiltInRegistries.ITEMS.register(RubyHelmetItem.KEY, new RubyHelmetItem());
        BuiltInRegistries.ITEMS.register(RubyChestplateItem.KEY, new RubyChestplateItem());
        BuiltInRegistries.ITEMS.register(RubyLeggingsItem.KEY, new RubyLeggingsItem());
        BuiltInRegistries.ITEMS.register(RubyBootsItem.KEY, new RubyBootsItem());

        registerBlock(TestBlock.KEY, new TestBlock());
        registerBlock(RubyBlock.KEY, new RubyBlock());
        registerBlock(RubyOreBlock.KEY, new RubyOreBlock());
        registerBlock(DeepslateRubyOreBlock.KEY, new DeepslateRubyOreBlock());

        registerFurniture(TestFurniture.KEY, new TestFurniture());

        CustomBlockPopulator populator = new CustomBlockPopulator(PopulatorSettings.builder()
                .veinSize(5)
                .replace(List.of(Material.STONE, Material.DEEPSLATE))
                .maxLevel(50)
                .minLevel(-50)
                .clusterChance(0.5)
                .iterations(10)
                .chance(1.0)
                .block(BuiltInRegistries.BLOCKS.get(RubyBlock.KEY))
        );

        Bukkit.getWorlds().forEach(world -> world.getPopulators().add(populator));
    }

    @EventHandler
    public void onPack(GenerateResourcePackEvent event) {
        event.addPluginPack(this);
    }

    private static void registerBlock(NamespacedKey key, CustomBlock block) {
        BuiltInRegistries.ITEMS.register(key, new CustomBlockItem(block.getKey(), ItemProperties.builder().model(block.properties().model()).build(), block));
        BuiltInRegistries.BLOCKS.register(key, block);
    }

    private static void registerFurniture(NamespacedKey key, CustomFurniture furniture) {
        BuiltInRegistries.ITEMS.register(key, new CustomFurnitureItem(furniture.getKey(), ItemProperties.builder().model(furniture.properties().model().model()).build(), furniture));
        BuiltInRegistries.FURNITURE.register(key, furniture);
    }
}
