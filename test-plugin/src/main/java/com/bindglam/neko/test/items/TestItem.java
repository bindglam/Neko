package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.CustomItemProperties;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemType;

import java.util.List;

public class TestItem extends CustomItem {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "testitem");

    public TestItem() {
        super(KEY, CustomItemProperties.builder().type(ItemType.PAPER)
                .name(Component.text("테스트 아이템")).lore(List.of(Component.text("테스트 아이템이다.")))
                .model(new NamespacedKey("defaultassets", "item/testitem")));
    }
}
