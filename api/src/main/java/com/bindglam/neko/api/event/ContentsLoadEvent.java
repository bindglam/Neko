package com.bindglam.neko.api.event;

import com.bindglam.neko.api.NekoProvider;
import com.bindglam.neko.api.content.Factory;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.block.Block;
import com.bindglam.neko.api.registry.Holder;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import com.bindglam.neko.api.registry.WritableRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ContentsLoadEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @ApiStatus.Internal
    public ContentsLoadEvent() {
    }

    public void registerItem(NamespacedKey key, Item item) {
        WritableRegistry<Item> registry = (WritableRegistry<Item>) BuiltInRegistries.ITEMS;

        registry.register(key, item);
    }

    public void registerBlock(NamespacedKey key, Block block) {
        WritableRegistry<Block> blockRegistry = (WritableRegistry<Block>) BuiltInRegistries.BLOCKS;
        WritableRegistry<Item> itemRegistry = (WritableRegistry<Item>) BuiltInRegistries.ITEMS;

        Block newBlock = NekoProvider.neko().nms().registerForNMS(block);

        blockRegistry.register(key, newBlock);
        itemRegistry.register(key, newBlock);
    }

    public void registerBlockRenderer(NamespacedKey key, Factory<BlockRenderer, Block> renderer) {
        WritableRegistry<Factory<BlockRenderer, Block>> registry = (WritableRegistry<Factory<BlockRenderer, Block>>) BuiltInRegistries.BLOCK_RENDERERS;

        registry.register(key, renderer);
    }

    public void registerGlyph(NamespacedKey key, Glyph glyph) {
        WritableRegistry<Glyph> registry = (WritableRegistry<Glyph>) BuiltInRegistries.GLYPHS;

        registry.register(key, glyph);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
