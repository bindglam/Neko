package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.Attributes;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;

public class RubyHelmetItem extends CustomItem {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_helmet");

    public RubyHelmetItem() {
        super(KEY, ItemProperties.builder()
                .type(ItemType.NETHERITE_HELMET)
                .durability(814)
                .model(new NamespacedKey("defaultassets", "item/ruby_helmet"))
                .armor(Armor.builder()
                        .slot(EquipmentSlot.HEAD)
                        .model(new NamespacedKey("defaultassets", "ruby"))
                        .isDispensable(true)
                        .isDamageOnHurt(true)
                        .isSwappable(true)
                        .isEquipOnInteract(true))
                .attributes(Attributes.builder()
                        .modifier(Attribute.ARMOR, 100.0, EquipmentSlotGroup.HEAD)
                        .resetWhenApply(true))
                .build());
    }
}
