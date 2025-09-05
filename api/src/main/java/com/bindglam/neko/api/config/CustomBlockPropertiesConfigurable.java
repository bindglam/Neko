package com.bindglam.neko.api.config;

import com.bindglam.neko.api.content.item.block.CustomBlockProperties;
import com.bindglam.neko.api.registry.BuiltInRegistries;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomBlockPropertiesConfigurable implements Configurable<CustomBlockProperties, ConfigurationSection> {
    @Override
    public @Nullable CustomBlockProperties load(@Nullable ConfigurationSection config) {
        if(config == null) return null;

        return new CustomBlockProperties(
                Objects.requireNonNull(Configurable.KEY.load(config.getString("block-model"))),
                BuiltInRegistries.MECHANISMS.get(Configurable.KEY.load(config.getString("mechanism")))
        );
    }
}
