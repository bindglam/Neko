package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.ContentRegistryEntry;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import org.jetbrains.annotations.NotNull;

public interface ItemRegistryEntry extends ContentRegistryEntry<ItemRegistryEntry, Item> {
    @NotNull ItemRegistryEntry properties(@NotNull ItemProperties properties);
}
