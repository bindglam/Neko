package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.content.item.CustomItemImpl
import com.bindglam.neko.content.item.block.CustomBlockImpl
import com.bindglam.neko.content.item.block.mechanism.NoteBlockMechanismFactory
import com.bindglam.neko.utils.CUSTOM_BLOCK_PROPERTIES_CONFIGURABLE
import com.bindglam.neko.utils.CUSTOM_ITEM_PROPERTIES_CONFIGURABLE
import com.bindglam.neko.utils.NamespacedKeyDataType
import com.bindglam.neko.utils.listFilesRecursively
import com.bindglam.neko.utils.plugin
import de.tr7zw.changeme.nbtapi.NBT
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.BlockState
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.slf4j.LoggerFactory
import java.io.File

object ContentManagerImpl : ContentManager {
    private val LOGGER = LoggerFactory.getLogger(ContentManager::class.java)

    private val CONTENTS_FOLDER = File("plugins/Neko/contents")

    override fun start() {
        BuiltInRegistries.MECHANISMS.lock { event ->
            event.register(NamespacedKey(NekoProvider.neko().plugin(), "note-block"), NoteBlockMechanismFactory())
        }

        if(!CONTENTS_FOLDER.exists())
            CONTENTS_FOLDER.mkdirs()

        var cnt = 0

        BuiltInRegistries.ITEMS.lock { itemRegistry ->
            BuiltInRegistries.BLOCKS.lock { blockRegistry ->
                CONTENTS_FOLDER.listFilesRecursively().forEach { file ->
                    YamlConfiguration.loadConfiguration(file).apply {
                        getKeys(false).stream().map { NamespacedKey.fromString(it)!! }.forEach { key ->
                            val config = getConfigurationSection(key.asString())!!

                            when(config.getString("type")) {
                                "item" -> {
                                    CustomItemImpl(key, CUSTOM_ITEM_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.item")!!)!!).also {
                                        itemRegistry.register(key, it)
                                    }
                                }

                                "block" -> {
                                    CustomBlockImpl(key, CUSTOM_ITEM_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.item")!!)!!, CUSTOM_BLOCK_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.block")!!)!!).also {
                                        itemRegistry.register(key, it)
                                        blockRegistry.register(key, it)
                                    }
                                }
                            }

                            cnt++
                        }
                    }
                }
            }
        }

        LOGGER.info("$cnt items loaded")
    }

    override fun end() {
    }

    override fun customItem(key: Key): CustomItem? = BuiltInRegistries.ITEMS.getOrNull(key)

    override fun customItem(itemStack: ItemStack): CustomItem? {
        if(itemStack.amount == 0 || itemStack.type == Material.AIR)
            return null

        val nbt = NBT.readNbt(itemStack)

        if(!nbt.hasTag(CustomItemImpl.ITEM_KEY_TAG))
            return null

        return customItem(Key.key(nbt.getString(CustomItemImpl.ITEM_KEY_TAG)))
    }

    override fun customBlock(key: Key): CustomBlock? = BuiltInRegistries.BLOCKS.getOrNull(key)

    override fun customBlock(block: BlockState): CustomBlock? {
        BuiltInRegistries.BLOCKS.filter { it.mechanism().isSame(block) }.also {
            return if(it.isEmpty()) null else it[0]
        }
    }
}