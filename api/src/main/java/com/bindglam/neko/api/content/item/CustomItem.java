package com.bindglam.neko.api.content.item;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomItem implements Keyed, ItemStackHolder {
    private static final NamespacedKey NEKO_ITEM_PDC_KEY = new NamespacedKey("neko", "item");

    private final NamespacedKey key;
    private final CustomItemProperties properties;

    private ItemStack itemStack;

    public CustomItem(NamespacedKey key, CustomItemProperties itemProperties) {
        this.key = key;
        this.properties = itemProperties;

        buildItemStack();
    }

    private void buildItemStack() {
        itemStack = properties.type().createItemStack();

        itemStack.editMeta((meta) -> {
            meta.itemName(properties.name());
            meta.lore(properties.lore());

            if(properties.model() != null)
                meta.setItemModel(key);
        });

        itemStack.editPersistentDataContainer((dataContainer) -> {
            dataContainer.set(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING, key.toString());
        });
    }

    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean isSame(ItemStack other) {
        return Objects.equals(other.getPersistentDataContainer().get(NEKO_ITEM_PDC_KEY, PersistentDataType.STRING), key.toString());
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @NotNull
    public CustomItemProperties itemProperties() {
        return properties;
    }
}
