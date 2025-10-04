package com.bindglam.neko.api.manager;

import com.bindglam.neko.api.content.block.Block;
import com.bindglam.neko.api.content.furniture.Furniture;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.item.Item;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ContentManager extends ManagerBase, Reloadable {
    @Nullable Item customItem(Key key);

    @Nullable Item customItem(ItemStack itemStack);

    @Nullable Block customBlock(Key key);

    @Nullable Block customBlock(BlockState block);

    default @Nullable Block customBlock(org.bukkit.block.Block block) {
        return customBlock(block.getState());
    }

    @Nullable Glyph glyph(Key key);

    @Nullable Furniture furniture(Key key);

    @Nullable Furniture furniture(Location location);
}
