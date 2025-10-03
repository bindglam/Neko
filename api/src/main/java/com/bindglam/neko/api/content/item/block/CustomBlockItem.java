package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomBlockItem extends CustomItem implements BlockItem {
    private final Block block;

    public CustomBlockItem(NamespacedKey key, ItemProperties itemProperties, Block block) {
        super(key, itemProperties);
        this.block = block;
    }

    @Override
    public @NotNull Block block() {
        return block;
    }
}
