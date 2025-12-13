package com.bindglam.neko.manager

import com.bindglam.neko.api.content.block.Block
import com.bindglam.neko.api.content.furniture.Furniture
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.item.Item
import com.bindglam.neko.api.event.AsyncContentsLoadEvent
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.manager.LifecycleContext
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.content.glyph.GlyphLoader
import com.bindglam.neko.content.glyph.ShiftGlyph
import com.bindglam.neko.content.item.CustomItemLoader
import com.bindglam.neko.content.block.CustomBlockLoader
import com.bindglam.neko.content.block.renderer.NoteBlockRenderer
import com.bindglam.neko.content.block.renderer.NoteBlockRendererFactory
import com.bindglam.neko.content.furniture.FurnitureLoader
import com.bindglam.neko.utils.listFilesRecursively
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.block.BlockState
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.slf4j.LoggerFactory
import java.io.File

object ContentManagerImpl : ContentManager {
    private val LOGGER = LoggerFactory.getLogger(ContentManager::class.java)

    private val CONTENTS_FOLDER = File("plugins/Neko/contents")

    private val CONTENT_LOADERS = listOf(CustomItemLoader, CustomBlockLoader, GlyphLoader, FurnitureLoader)

    override fun start(context: LifecycleContext, process: Process) {
        registerInternalContents()

        AsyncContentsLoadEvent().callEvent()

        val startMillis = System.currentTimeMillis()

        if(!CONTENTS_FOLDER.exists())
            CONTENTS_FOLDER.mkdirs()

        CONTENTS_FOLDER.listFilesRecursively().forEach { file ->
            YamlConfiguration.loadConfiguration(file).apply {
                getKeys(false).stream().map { Key.key(it) }.forEach { key ->
                    val config = getConfigurationSection(key.asString())!!

                    CONTENT_LOADERS.find { it.id() == config.getString("type")!! }!!.load(key, config)
                }
            }
        }

        LOGGER.info("${BuiltInRegistries.ITEMS.size()} items loaded")
        LOGGER.info("${BuiltInRegistries.BLOCKS.size()} blocks loaded")
        LOGGER.info("${BuiltInRegistries.GLYPHS.size()} glyphs loaded")
        LOGGER.info("Successfully loaded in ${System.currentTimeMillis() - startMillis}ms")
    }

    private fun registerInternalContents() {
        BuiltInRegistries.BLOCK_RENDERERS.register(NoteBlockRenderer.KEY, NoteBlockRendererFactory)

        BuiltInRegistries.GLYPHS.register(Glyph.SHIFT_GLYPH_KEY, ShiftGlyph())
    }

    override fun end(context: LifecycleContext, process: Process) {
        BuiltInRegistries.ITEMS.clear()
        BuiltInRegistries.BLOCKS.clear()
        BuiltInRegistries.GLYPHS.clear()
    }

    override fun customItem(key: Key): Item? = BuiltInRegistries.ITEMS.get(key).orElse(null)
    override fun customItem(itemStack: ItemStack): Item? = BuiltInRegistries.ITEMS.find { it.isSame(itemStack) }
    override fun customBlock(key: Key): Block? = BuiltInRegistries.BLOCKS.get(key).orElse(null)
    override fun customBlock(block: BlockState): Block? = BuiltInRegistries.BLOCKS.find { it.isSame(block) }
    override fun glyph(key: Key): Glyph? = BuiltInRegistries.GLYPHS.get(key).orElse(null)
    override fun furniture(key: Key): Furniture? = BuiltInRegistries.FURNITURE.get(key).orElse(null)
    override fun furniture(location: Location): Furniture? = BuiltInRegistries.FURNITURE.find { it.isSame(location) }
}