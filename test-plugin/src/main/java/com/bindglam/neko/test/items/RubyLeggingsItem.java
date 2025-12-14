package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemType;

public class RubyLeggingsItem extends Item {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_leggings");

    public RubyLeggingsItem() {
        super(KEY, ItemProperties.builder()
                .type(ItemType.NETHERITE_LEGGINGS)
                .durability(1110)
                .model(new NamespacedKey("defaultassets", "item/ruby_leggings"))
                .armor(Armor.builder()
                        .slot(EquipmentSlot.LEGS)
                        .model(new NamespacedKey("defaultassets", "ruby"))
                        .isDispensable(true)
                        .isDamageOnHurt(true)
                        .isSwappable(true)
                        .isEquipOnInteract(true)
                        .build())
                .build());
    }
}
