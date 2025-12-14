package com.bindglam.neko.content.block

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.block.Block
import com.bindglam.neko.api.content.item.block.BlockItem
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.content.CustomBlockPropertiesConfigurable
import com.bindglam.neko.config.content.CustomItemPropertiesConfigurable
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

object CustomBlockLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        Block(
            NamespacedKey(key.namespace(), key.value()),
            CustomBlockPropertiesConfigurable.load(config.getConfigurationSection("properties.block")!!)!!
        ).also { block ->
            config.getConfigurationSection("properties.item")?.let { BuiltInRegistries.ITEMS.register(key,
                BlockItem(block.key, CustomItemPropertiesConfigurable.load(it), block)
            ) }
            BuiltInRegistries.BLOCKS.register(key, block)
        }
    }

    override fun id(): String = "block"
}