package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.mechanism.BlockMechanism;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomBlock extends CustomItem {
    private final CustomBlockProperties properties;

    private final BlockMechanism mechanism;

    public CustomBlock(NamespacedKey key, CustomItemProperties itemProperties, CustomBlockProperties blockProperties) {
        super(key, itemProperties);
        this.properties = blockProperties;
        this.mechanism = properties.mechanismFactory().create(this);
    }

    @Override
    public void pack(@NotNull PackZipper zipper) {
        super.pack(zipper);

        if(mechanism instanceof Packable packable)
            packable.pack(zipper);
    }

    @NotNull
    public CustomBlockProperties blockProperties() {
        return properties;
    }

    @NotNull
    public BlockMechanism mechanism() {
        return mechanism;
    }
}
