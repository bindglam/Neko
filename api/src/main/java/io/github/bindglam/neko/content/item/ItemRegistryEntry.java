package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.registry.WritableRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface ItemRegistryEntry extends WritableRegistry.RegistryEntry<Item> {
    @NotNull ItemRegistryEntry key(@NotNull Key key);

    @NotNull ItemRegistryEntry properties(@NotNull ItemProperties properties);
}
