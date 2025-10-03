package com.bindglam.neko.api.content.item;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ItemBuilder {
    private ItemBuilder() {
    }

    public static @NotNull ItemStack build(Item item) {
        ItemStack itemStack = item.properties().type().createItemStack();

        itemStack.editMeta((meta) -> {
            if(item.properties().name() == null)
                meta.itemName(Component.translatable(item));
            else
                meta.itemName(item.properties().name());
            if(meta instanceof Damageable damageable) {
                Integer durability = item.properties().durability();
                if(durability != null && durability != 0)
                    damageable.setMaxDamage(durability);
            }
            meta.lore(item.properties().lore());
            meta.setItemModel(item.getKey());

            if(item.properties().armor() != null) {
                EquippableComponent equippable = meta.getEquippable();
                Objects.requireNonNull(item.properties().armor()).apply(equippable);
                meta.setEquippable(equippable);
            }
        });

        itemStack.editPersistentDataContainer((dataContainer) -> dataContainer.set(Item.NEKO_ITEM_PDC_KEY, PersistentDataType.STRING, item.getKey().toString()));

        return itemStack;
    }
}
