package com.bindglam.neko.api.content.block;

import com.bindglam.neko.api.content.EventContainer;
import com.bindglam.neko.api.content.EventHandler;
import com.bindglam.neko.api.content.block.properties.BlockProperties;
import com.bindglam.neko.api.content.block.renderer.BlockRenderer;
import com.bindglam.neko.api.content.item.Item;
import com.bindglam.neko.api.content.item.ItemLike;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Block implements Keyed, ItemLike, Translatable, EventContainer, Packable {
    private final NamespacedKey key;
    private final BlockProperties properties;
    private final EventHandler eventHandler;

    private final BlockRenderer renderer;

    public Block(NamespacedKey key, BlockProperties blockProperties, EventHandler eventHandler) {
        this.key = key;
        this.properties = blockProperties;
        this.eventHandler = eventHandler;

        this.renderer = properties.renderer().create(this);
    }

    public Block(NamespacedKey key, BlockProperties blockProperties) {
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

    public @NotNull BlockState blockState() {
        return renderer.createBlockState();
    }

    public boolean isSame(BlockState other) {
        return renderer.isSame(other);
    }

    @NotNull
    public BlockProperties properties() {
        return properties;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }

    @Override
    public @NotNull String translationKey() {
        return "block." + getKey().getNamespace() + "." + getKey().getKey();
    }

    @Override
    public @Nullable Item asItem() {
        return BuiltInRegistries.ITEMS.get(getKey()).orElse(null);
    }
}
