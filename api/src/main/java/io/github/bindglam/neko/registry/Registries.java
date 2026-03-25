package io.github.bindglam.neko.registry;

import io.github.bindglam.neko.content.item.Item;
import io.github.bindglam.neko.content.item.ItemRegistryEntry;
import org.jetbrains.annotations.NotNull;

public interface Registries {
    @NotNull EntryWritableRegistry<Item, ItemRegistryEntry> item();

    void lockAll();

    void unlockAll();
}
