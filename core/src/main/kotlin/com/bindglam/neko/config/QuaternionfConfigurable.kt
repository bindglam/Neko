package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import org.bukkit.configuration.ConfigurationSection
import org.joml.Quaternionf

object QuaternionfConfigurable : Configurable<Quaternionf, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): Quaternionf? = config?.let { Quaternionf()
        .rotateX(Math.toRadians(config.getDouble("x")).toFloat())
        .rotateY(Math.toRadians(config.getDouble("y")).toFloat())
        .rotateZ(Math.toRadians(config.getDouble("z")).toFloat())
    } ?: Quaternionf()
}