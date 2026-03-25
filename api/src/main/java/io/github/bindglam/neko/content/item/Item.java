package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.Content;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Item extends Content<Item>, Translatable, ItemLike {
    @NotNull ItemProperties properties();

    @NotNull ImmutableItemStack itemStack();

    boolean isSimilar(@NotNull ItemStack itemStack);
}
