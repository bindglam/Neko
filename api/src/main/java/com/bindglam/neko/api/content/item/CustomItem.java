package com.bindglam.neko.api.content.item;

import com.bindglam.neko.api.pack.Packable;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface CustomItem extends Keyed, ItemStackConvertible, Packable {
    @NotNull CustomItemProperties itemProperties();
}
