package com.bindglam.neko.api.item;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface CustomItem extends Keyed, ItemStackConvertible {
    @NotNull CustomItemProperties properties();
}
