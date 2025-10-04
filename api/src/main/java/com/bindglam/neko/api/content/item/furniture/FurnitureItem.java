package com.bindglam.neko.api.content.item.furniture;

import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.item.Item;
import org.jetbrains.annotations.NotNull;

public interface FurnitureItem extends Item {
    @NotNull Furniture furniture();

    @Override
    @NotNull
    default String translationKey() {
        return furniture().translationKey();
    }
}
