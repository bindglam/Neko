package com.bindglam.neko.manager

import com.bindglam.neko.api.item.CustomItemProperties
import com.bindglam.neko.api.manager.CacheManager
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.item.CustomItemImpl
import com.bindglam.neko.utils.listFilesRecursively
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.slf4j.LoggerFactory
import java.io.File

object ContentManagerImpl : ContentManager {
    private val LOGGER = LoggerFactory.getLogger(ContentManager::class.java)

    private val CONTENTS_FOLDER = File("plugins/Neko/contents")

    override fun start() {
        if(!CONTENTS_FOLDER.exists())
            CONTENTS_FOLDER.mkdirs()

        var cnt = 0

        BuiltInRegistries.ITEMS.lock { event ->
            CONTENTS_FOLDER.listFilesRecursively().forEach { file ->
                YamlConfiguration.loadConfiguration(file).apply {
                    getKeys(false).stream().map { NamespacedKey.fromString(it)!! }.forEach { key ->
                        val config = getConfigurationSection(key.asString())!!

                        val customItem = CustomItemImpl(key, CustomItemProperties.fromConfig(config.getConfigurationSection("properties")!!))

                        event.register(key, customItem)

                        cnt++
                    }
                }
            }
        }

        LOGGER.info("$cnt items loaded")
    }

    override fun end() {
    }
}