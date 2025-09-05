package com.bindglam.neko.api.content.item.block.mechanism;

import com.bindglam.neko.api.content.item.block.CustomBlock;
import org.jetbrains.annotations.NotNull;

public interface MechanismFactory<T extends Mechanism> {
    @NotNull T create(@NotNull CustomBlock block);
}
