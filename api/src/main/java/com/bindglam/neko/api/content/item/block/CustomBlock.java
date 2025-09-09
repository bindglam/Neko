package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.block.mechanism.BlockMechanism;
import org.jetbrains.annotations.NotNull;

public interface CustomBlock extends CustomItem {
    @NotNull CustomBlockProperties blockProperties();

    @NotNull BlockMechanism mechanism();
}
