package com.bindglam.neko.api.content.item.furniture;

import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class FurnitureItem extends Item {
    private final Furniture furniture;

    public FurnitureItem(NamespacedKey key, ItemProperties itemProperties, Furniture furniture) {
        super(key, itemProperties);
        this.furniture = furniture;
    }

    public @NotNull Furniture furniture() {
        return furniture;
    }

    @Override
    public @NotNull String translationKey() {
        return furniture.translationKey();
    }
}
