package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.NamespacedKey
import org.bukkit.Tag

class TagConfigurable<T : Keyed>(private val registry: String, private val clazz: Class<T>) : Configurable<Tag<T>, String> {
    override fun load(value: String?): Tag<T>? = value?.let { Bukkit.getTag(registry, NamespacedKey.fromString(value)!!, clazz) }
}