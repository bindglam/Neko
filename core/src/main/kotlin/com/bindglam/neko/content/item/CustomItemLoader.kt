package com.bindglam.neko.content.item

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.item.Item
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.content.CustomItemPropertiesConfigurable
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

object CustomItemLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection){
        Item(
            NamespacedKey(key.namespace(), key.value()),
            CustomItemPropertiesConfigurable.load(config.getConfigurationSection("properties.item"))!!
        ).also {
            BuiltInRegistries.ITEMS.register(key, it)
        }
    }

    override fun id(): String = "item"
}