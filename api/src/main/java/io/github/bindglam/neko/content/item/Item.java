package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.content.Content;
import io.github.bindglam.neko.content.item.properties.ItemProperties;
import io.github.bindglam.neko.platform.PlatformItemStack;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface Item extends Content, Translatable, ItemLike {
    @NotNull ItemProperties properties();

    @NotNull ImmutableItemStack itemStack();

    boolean isSimilar(@NotNull PlatformItemStack itemStack);
}
