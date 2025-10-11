package com.bindglam.neko.api.content.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemStackHolder {
    @NotNull ItemStack itemStack();

    boolean isSame(ItemStack other);
}
