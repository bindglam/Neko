package com.bindglam.neko.manager

import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.content.glyph.GlyphLoader
import com.bindglam.neko.content.glyph.ShiftGlyph
import com.bindglam.neko.content.item.CustomItemLoader
import com.bindglam.neko.content.item.block.CustomBlockLoader
import com.bindglam.neko.content.item.block.renderer.NoteBlockRenderer
import com.bindglam.neko.content.item.block.renderer.NoteBlockRendererFactory
import com.bindglam.neko.utils.listFilesRecursively
import net.kyori.adventure.key.Key
import org.bukkit.block.BlockState
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files

object ContentManagerImpl : ContentManager {
    private val LOGGER = LoggerFactory.getLogger(ContentManager::class.java)

    private val CONTENTS_FOLDER = File("plugins/Neko/contents")

    private val CONTENT_LOADERS = listOf(CustomItemLoader(), CustomBlockLoader(), GlyphLoader())

    override fun start(process: Process) {
        registerInternalContents()

        val startMillis = System.currentTimeMillis()

        if(!CONTENTS_FOLDER.exists())
            CONTENTS_FOLDER.mkdirs()

        process.forEachParallel(CONTENTS_FOLDER.listFilesRecursively(), { Files.size(it.toPath()) }) { file ->
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
        BuiltInRegistries.BLOCK_RENDERERS.register(NoteBlockRenderer.KEY, NoteBlockRendererFactory())

        BuiltInRegistries.GLYPHS.register(Glyph.SHIFT_GLYPH_KEY, ShiftGlyph())
    }

    override fun end(process: Process) {
        BuiltInRegistries.ITEMS.clear()
        BuiltInRegistries.BLOCKS.clear()
        BuiltInRegistries.GLYPHS.clear()
    }

    override fun customItem(key: Key): CustomItem? = BuiltInRegistries.ITEMS.getOrNull(key)
    override fun customItem(itemStack: ItemStack): CustomItem? = BuiltInRegistries.ITEMS.find { it.isSame(itemStack) }
    override fun customBlock(key: Key): CustomBlock? = BuiltInRegistries.BLOCKS.getOrNull(key)
    override fun customBlock(block: BlockState): CustomBlock? = BuiltInRegistries.BLOCKS.find { it.renderer().isSame(block) }
    override fun glyph(key: Key): Glyph? = BuiltInRegistries.GLYPHS.getOrNull(key)
}