package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class BlockItem extends Item {
    private final Block block;

    public BlockItem(NamespacedKey key, ItemProperties itemProperties, Block block) {
        super(key, itemProperties);
        this.block = block;
    }

    public @NotNull Block block() {
        return block;
    }

    @Override
    public @NotNull String translationKey() {
        return block.translationKey();
    }
}
