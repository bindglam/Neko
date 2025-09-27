package com.bindglam.neko.api.content.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class ItemBuilder {
    private ItemBuilder() {
    }

    public static @NotNull ItemStack build(Item item) {
        ItemStack itemStack = item.properties().type().createItemStack();

        itemStack.editMeta((meta) -> {
            meta.itemName(item.properties().name());
            meta.lore(item.properties().lore());
            meta.setItemModel(item.getKey());
        });

        itemStack.editPersistentDataContainer((dataContainer) -> dataContainer.set(Item.NEKO_ITEM_PDC_KEY, PersistentDataType.STRING, item.getKey().toString()));

        return itemStack;
    }
}
