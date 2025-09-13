package com.bindglam.neko.api.content.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CustomItemProperties(
        @NotNull ItemType type,
        @Nullable Component name,
        @Nullable List<Component> lore,
        @Nullable Key model
){
}
