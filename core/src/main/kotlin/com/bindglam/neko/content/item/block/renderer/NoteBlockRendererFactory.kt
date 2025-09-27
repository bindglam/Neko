package com.bindglam.neko.content.item.block.renderer

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.Factory
import com.bindglam.neko.api.content.block.Block
import com.bindglam.neko.api.content.block.renderer.BlockRenderer
import com.bindglam.neko.api.pack.minecraft.block.VanillaInstruments
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory

object NoteBlockRendererFactory : Factory<BlockRenderer, Block> {
    private val LOGGER = LoggerFactory.getLogger(NoteBlockRendererFactory::class.java)

    init {
        LOGGER.info("Available ${VanillaInstruments.entries.size * 24 * 2 - 24} note block states")

        Bukkit.getPluginManager().registerEvents(NoteBlockRendererListener(), NekoProvider.neko().plugin())
    }

    override fun create(block: Block): BlockRenderer = NoteBlockRenderer(block)
}