package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.block.mechanism.MechanismFactory;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record CustomBlockProperties(
        @NotNull Key blockModel,
        @NotNull MechanismFactory<?> mechanismFactory
) {
}
