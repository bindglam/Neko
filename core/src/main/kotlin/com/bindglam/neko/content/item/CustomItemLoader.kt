package com.bindglam.neko.content.item

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.CUSTOM_ITEM_PROPERTIES_CONFIGURABLE
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

class CustomItemLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection){
        CustomItem(NamespacedKey(key.namespace(), key.value()), CUSTOM_ITEM_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.item"))!!).also {
            BuiltInRegistries.ITEMS.register(key, it)
        }
    }

    override fun id(): String = "item"
}