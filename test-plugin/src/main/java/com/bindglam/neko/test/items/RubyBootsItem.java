package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.properties.Armor;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemType;

public class RubyBootsItem extends CustomItem {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby_boots");

    public RubyBootsItem() {
        super(KEY, ItemProperties.builder()
                .type(ItemType.NETHERITE_BOOTS)
                .durability(962)
                .model(new NamespacedKey("defaultassets", "item/ruby_boots"))
                .armor(Armor.builder()
                        .slot(EquipmentSlot.FEET)
                        .model(new NamespacedKey("defaultassets", "ruby"))
                        .isDispensable(true)
                        .isDamageOnHurt(true)
                        .isSwappable(true)
                        .isEquipOnInteract(true)
                        .build())
                .build());
    }
}
