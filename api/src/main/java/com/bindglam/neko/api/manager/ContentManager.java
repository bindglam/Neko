package com.bindglam.neko.api.manager;

import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import net.kyori.adventure.key.Key;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ContentManager extends ManagerBase, Reloadable {
    @Nullable CustomItem customItem(Key key);

    @Nullable CustomItem customItem(ItemStack itemStack);

    @Nullable CustomBlock customBlock(Key key);

    @Nullable CustomBlock customBlock(BlockState block);

    default @Nullable CustomBlock customBlock(Block block) {
        return customBlock(block.getState());
    }

    @Nullable Glyph glyph(Key key);
}
