package com.bindglam.neko.content.item.block

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.CUSTOM_BLOCK_PROPERTIES_CONFIGURABLE
import com.bindglam.neko.utils.CUSTOM_ITEM_PROPERTIES_CONFIGURABLE
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

class CustomBlockLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        CustomBlock(NamespacedKey(key.namespace(), key.value()), CUSTOM_ITEM_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.item")!!)!!, CUSTOM_BLOCK_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.block")!!)!!).also {
            BuiltInRegistries.ITEMS.register(key, it)
            BuiltInRegistries.BLOCKS.register(key, it)
        }
    }

    override fun id(): String = "block"
}