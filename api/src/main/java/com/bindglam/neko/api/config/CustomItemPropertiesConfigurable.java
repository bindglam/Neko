package com.bindglam.neko.api.config;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomItemPropertiesConfigurable implements Configurable<CustomItemProperties, ConfigurationSection> {
    @Override
    public @Nullable CustomItemProperties load(@Nullable ConfigurationSection config) {
        if(config == null) return null;

        return new CustomItemProperties(
                Objects.requireNonNull(RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(Objects.requireNonNull(Configurable.KEY.load(config.getString("type")))), "itemType cannot be null"),
                Configurable.COMPONENT.load(config.getString("display-name")),
                Configurable.COMPONENT_LIST.load(config.getStringList("lore")),
                Configurable.KEY.load(config.getString("item-model"))
        );
    }
}
