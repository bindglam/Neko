package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.EventHandler;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class CustomBlock implements Block, Packable {
    private final NamespacedKey key;
    private final BlockProperties properties;
    private final EventHandler eventHandler;

    private final BlockRenderer renderer;

    public CustomBlock(NamespacedKey key, BlockProperties blockProperties, EventHandler eventHandler) {
        this.key = key;
        this.properties = blockProperties;
        this.eventHandler = eventHandler;

        this.renderer = properties.renderer().create(this);
    }

    public CustomBlock(NamespacedKey key, BlockProperties blockProperties) {
        this(key, blockProperties, EventHandler.EMPTY);
    }

    @Override
    public @NotNull EventHandler eventHandler() {
        return eventHandler;
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
