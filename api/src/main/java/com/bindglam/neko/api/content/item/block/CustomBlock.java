package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.mechanism.Mechanism;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomBlock extends CustomItem {
    private final CustomBlockProperties properties;

    private final Mechanism mechanism;

    public CustomBlock(NamespacedKey key, CustomItemProperties itemProperties, CustomBlockProperties blockProperties) {
        super(key, itemProperties);
        this.properties = blockProperties;
        this.mechanism = properties.mechanismFactory().create(this);
    }

    @NotNull
    public CustomBlockProperties blockProperties() {
        return properties;
    }

    @NotNull
    public Mechanism mechanism() {
        return mechanism;
    }
}
