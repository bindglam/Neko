package com.bindglam.neko.api.content.item.furniture;

import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface FurnitureItem extends Item {
    @NotNull Furniture furniture();

    @Override
    @NotNull
    default String translationKey() {
        return furniture().translationKey();
    }
}
