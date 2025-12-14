package com.bindglam.neko.test.items;

import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.properties.ItemProperties;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

import java.util.List;

public class RubyItem extends Item {
    public static final NamespacedKey KEY = new NamespacedKey("defaultassets", "ruby");

    public RubyItem() {
        super(KEY, ItemProperties.builder()
                .lore(List.of(Component.text("반짝반짝")))
                .model(new NamespacedKey("defaultassets", "item/ruby"))
                .build());
    }
}
