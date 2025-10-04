package com.bindglam.neko.api.content.item.furniture;

import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomFurnitureItem extends CustomItem implements FurnitureItem {
    private final Furniture furniture;

    public CustomFurnitureItem(NamespacedKey key, ItemProperties itemProperties, Furniture furniture) {
        super(key, itemProperties);
        this.furniture = furniture;
    }

    @Override
    public @NotNull Furniture furniture() {
        return furniture;
    }
}
