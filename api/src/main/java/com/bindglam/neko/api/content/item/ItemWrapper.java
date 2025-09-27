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

public final class ItemWrapper implements Item {
    private final NamespacedKey key;

    private ItemWrapper(NamespacedKey key) {
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
    public @NotNull ItemProperties itemProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    public static ItemWrapper of(NamespacedKey key) {
        return new ItemWrapper(key);
    }

    public static ItemWrapper of(Material material) {
        return of(material.getKey());
    }

    public static ItemWrapper of(ItemType type) {
        return of(type.getKey());
    }

    public static ItemWrapper of(ItemStack itemStack) {
        return of(itemStack.getType());
    }

    public static ItemWrapper of(CustomItem customItem) {
        return of(customItem.getKey());
    }
}
