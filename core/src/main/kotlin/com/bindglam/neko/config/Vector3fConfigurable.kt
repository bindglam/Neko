package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import org.bukkit.configuration.ConfigurationSection
import org.joml.Vector3f

object Vector3fConfigurable : Configurable<Vector3f, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): Vector3f? = config?.let {
        Vector3f(config.getDouble("x").toFloat(), config.getDouble("y").toFloat(), config.getDouble("z").toFloat())
    } ?: Vector3f()
}