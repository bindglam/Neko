package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemType;

public class RubyChestplateItem extends Item {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_chestplate");

    public RubyChestplateItem() {
        super(KEY, ItemProperties.builder()
                .type(ItemType.NETHERITE_CHESTPLATE)
                .durability(1184)
                .model(new NamespacedKey("defaultassets", "item/ruby_chestplate"))
                .armor(Armor.builder()
                        .slot(EquipmentSlot.CHEST)
                        .model(new NamespacedKey("defaultassets", "ruby"))
                        .isDispensable(true)
                        .isDamageOnHurt(true)
                        .isSwappable(true)
                        .isEquipOnInteract(true)
                        .build())
                .build());
    }
}
