package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

object KeyConfigurable : Configurable<NamespacedKey, String> {
    override fun load(value: String?): NamespacedKey? = value?.let { NamespacedKey.fromString(value) }
}