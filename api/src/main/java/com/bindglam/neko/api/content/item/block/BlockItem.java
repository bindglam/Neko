package com.bindglam.neko.api.content.item.block;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.item.Item;
import org.jetbrains.annotations.NotNull;

public interface BlockItem extends Item {
    @NotNull Block block();

    @Override
    @NotNull
    default String translationKey() {
        return block().translationKey();
    }
}
