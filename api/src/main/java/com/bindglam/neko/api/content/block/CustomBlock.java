package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesData;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import com.bindglam.neko.api.pack.minecraft.model.ModelData;
import com.bindglam.neko.api.utils.GsonUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class CustomBlock implements Block, Packable {
    private final NamespacedKey key;
    private final BlockProperties properties;

    private final BlockRenderer renderer;

    public CustomBlock(NamespacedKey key, BlockProperties blockProperties) {
        this.key = key;
        this.properties = blockProperties;

        this.renderer = properties.renderer().create(this);
    }

    @ApiStatus.Internal
    @Override
    public void pack(@NotNull PackZipper zipper) {
        AtlasesMaker.addAllFromModel(properties.model(), zipper);

        if(renderer instanceof Packable packable)
            packable.pack(zipper);
    }

    @NotNull
    public BlockRenderer renderer() {
        return renderer;
    }

    @Override
    public @NotNull BlockState blockState() {
        return renderer.createBlockState();
    }

    @Override
    public boolean isSame(BlockState other) {
        return renderer.isSame(other);
    }

    @Override
    @NotNull
    public BlockProperties properties() {
        return properties;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }
}
