package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.furniture.properties.FurnitureProperties
import com.bindglam.neko.api.content.furniture.properties.Model
import com.bindglam.neko.config.KeyConfigurable
import org.bukkit.configuration.ConfigurationSection
import org.joml.Quaternionf
import org.joml.Vector3f

object FurniturePropertiesConfigurable : Configurable<FurnitureProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): FurnitureProperties? = config?.let { FurnitureProperties.builder()
        .model(ModelConfigurable.load(config.getConfigurationSection("model")))
        .build()
    }

    private object ModelConfigurable : Configurable<Model, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Model? = config?.let { Model.builder()
            .model(KeyConfigurable.load(config.getString("model")))
            .translation(Vector3f(config.getDouble("translation.x").toFloat(), config.getDouble("translation.y").toFloat(), config.getDouble("translation.z").toFloat()))
            .rotation(Quaternionf()
                .rotateX(Math.toRadians(config.getDouble("rotation.x")).toFloat())
                .rotateY(Math.toRadians(config.getDouble("rotation.y")).toFloat())
                .rotateZ(Math.toRadians(config.getDouble("rotation.z")).toFloat()))
            .scale(Vector3f(config.getDouble("scale.x").toFloat(), config.getDouble("scale.y").toFloat(), config.getDouble("scale.z").toFloat()))
        }
    }
}