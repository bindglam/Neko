package com.bindglam.neko.content.furniture

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.furniture.CustomFurniture
import com.bindglam.neko.api.content.item.furniture.CustomFurnitureItem
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.CUSTOM_ITEM_PROPERTIES_CONFIGURABLE
import com.bindglam.neko.utils.FURNITURE_PROPERTIES_CONFIGURABLE
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

object FurnitureLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        CustomFurniture(NamespacedKey(key.namespace(), key.value()), FURNITURE_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.furniture")!!)!!).also { furniture ->
            config.getConfigurationSection("properties.item")?.let { BuiltInRegistries.ITEMS.register(key, CustomFurnitureItem(furniture.key, CUSTOM_ITEM_PROPERTIES_CONFIGURABLE.load(it), furniture)) }
            BuiltInRegistries.FURNITURE.register(key, furniture)
        }
    }

    override fun id(): String = "furniture"
}