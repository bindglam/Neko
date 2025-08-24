package com.bindglam.neko.api.item;

import com.bindglam.neko.api.config.Configurable;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public record CustomItemProperties(
        @NotNull ItemType itemType,
        @Nullable Component displayName,
        @Nullable List<Component> lore,
        @Nullable Key itemModel
) {
    public static @NotNull CustomItemProperties fromConfig(@NotNull ConfigurationSection config) {
        return new CustomItemProperties(
                Objects.requireNonNull(RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(Objects.requireNonNull(Configurable.KEY.load(config.getString("type")))), "itemType cannot be null"),
                Configurable.COMPONENT.load(config.getString("display-name")),
                Configurable.COMPONENT_LIST.load(config.getStringList("lore")),
                Configurable.KEY.load(config.getString("item-model"))
        );
    }
}
