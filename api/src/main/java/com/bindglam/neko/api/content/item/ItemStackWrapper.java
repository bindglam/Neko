package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.NekoProvider;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ItemStackWrapper implements ItemStackHolder {
    private final NamespacedKey key;

    private ItemStackWrapper(NamespacedKey key) {
        this.key = key;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        ItemType type = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(key);

        if(type != null)
            return type.createItemStack();

        return Objects.requireNonNull(NekoProvider.neko().contentManager().customItem(key)).itemStack();
    }

    @Override
    public boolean isSame(ItemStack other) {
        Item customItem = NekoProvider.neko().contentManager().customItem(key);

        if(customItem != null)
            return customItem.isSame(other);
        
        return other.getType().getKey().equals(key);
    }

    public static ItemStackWrapper of(NamespacedKey key) {
        return new ItemStackWrapper(key);
    }

    public static ItemStackWrapper of(Material material) {
        return of(material.getKey());
    }

    public static ItemStackWrapper of(ItemType type) {
        return of(type.getKey());
    }

    public static ItemStackWrapper of(ItemStack itemStack) {
        return of(itemStack.getType());
    }

    public static ItemStackWrapper of(CustomItem customItem) {
        return of(customItem.getKey());
    }
}
