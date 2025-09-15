package com.bindglam.neko.api.content.item.block.mechanism;

import com.bindglam.neko.api.content.Mechanism;
import com.bindglam.neko.api.content.item.block.CustomBlock;
import com.bindglam.neko.api.pack.Packer;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public interface BlockMechanism extends Mechanism<CustomBlock>, Packer<CustomBlock> {
    void place(@NotNull Location location);

    boolean isSame(@NotNull BlockState block);
}
