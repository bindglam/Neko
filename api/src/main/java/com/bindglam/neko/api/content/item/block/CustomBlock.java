package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.Builder;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class CustomBlock extends CustomItem {
    private final CustomBlockProperties properties;

    private final BlockRenderer renderer;

    public CustomBlock(NamespacedKey key, CustomItemProperties itemProperties, CustomBlockProperties blockProperties) {
        super(key, itemProperties);
        this.properties = blockProperties;
        this.renderer = properties.renderer().create(this);
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        super.pack(zipper);

        if(renderer instanceof Packable packable)
            packable.pack(zipper);
    }

    @NotNull
    public CustomBlockProperties blockProperties() {
        return properties;
    }

    @NotNull
    public BlockRenderer renderer() {
        return renderer;
    }
}
