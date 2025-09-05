package com.bindglam.neko.api.config;

import com.bindglam.neko.api.content.item.CustomItemProperties;
import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Configurable<T, C> {
    Configurable<Component, String> COMPONENT = new ComponentConfigurable();
    Configurable<List<Component>, List<String>> COMPONENT_LIST = new ComponentListConfigurable();
    Configurable<Key, String> KEY = new KeyConfigurable();
    Configurable<CustomItemProperties, ConfigurationSection> CUSTOM_ITEM_PROPERTIES = new CustomItemPropertiesConfigurable();
    Configurable<CustomBlockProperties, ConfigurationSection> CUSTOM_BLOCK_PROPERTIES = new CustomBlockPropertiesConfigurable();

    @Nullable T load(@Nullable C config);
}
