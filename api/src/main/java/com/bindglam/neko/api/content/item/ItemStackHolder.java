package com.bindglam.neko.api.content.item;

import org.bukkit.inventory.ItemStack;

public interface ItemStackHolder {
    ItemStack itemStack();

    default boolean isSame(ItemStack other) {
        return itemStack().isSimilar(other);
    }
}
