package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.Attributes;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ApiStatus.Internal
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
                int durability = item.properties().durability();
                if(durability != 0)
                    damageable.setMaxDamage(durability);
            }
            meta.lore(item.properties().lore());
            meta.setItemModel(item.getKey());

            if(item.properties().armor() != null) {
                Armor armor = Objects.requireNonNull(item.properties().armor());
                EquippableComponent equippable = meta.getEquippable();

                armor.apply(equippable);

                meta.setEquippable(equippable);
            }

            if(item.properties().attributes() != null) {
                Attributes attributes = Objects.requireNonNull(item.properties().attributes());
                Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();

                attributes.modifiers().forEach(modifiers::put);
                if(!attributes.resetWhenApply()) {
                    item.properties().type().getDefaultAttributeModifiers().forEach(((attribute, modifier) -> {
                        if (!modifiers.containsKey(attribute))
                            modifiers.put(attribute, modifier);
                    }));
                }

                meta.setAttributeModifiers(modifiers);
            }
        });

        itemStack.editPersistentDataContainer((dataContainer) -> dataContainer.set(Item.NEKO_ITEM_PDC_KEY, PersistentDataType.STRING, item.getKey().toString()));

        return itemStack;
    }
}
