package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties
import com.bindglam.neko.api.content.furniture.properties.Model
import com.bindglam.neko.config.KeyConfigurable
import com.bindglam.neko.config.QuaternionfConfigurable
import com.bindglam.neko.config.Vector3fConfigurable
import org.bukkit.configuration.ConfigurationSection

object FurniturePropertiesConfigurable : Configurable<FurnitureProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): FurnitureProperties? = config?.let { FurnitureProperties.builder()
        .model(ModelConfigurable.load(config.getConfigurationSection("model")))
        .build()
    }

    private object ModelConfigurable : Configurable<Model, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Model? = config?.let { Model.builder()
            .model(KeyConfigurable.load(config.getString("model")))
            .translation(Vector3fConfigurable.load(config.getConfigurationSection("translation")))
            .rotation(QuaternionfConfigurable.load(config.getConfigurationSection("rotation")))
            .scale(Vector3fConfigurable.load(config.getConfigurationSection("scale")))
        }
    }
}