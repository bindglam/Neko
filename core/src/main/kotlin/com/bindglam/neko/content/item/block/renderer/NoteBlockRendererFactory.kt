package com.bindglam.neko.content.item.block.renderer

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.Factory
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.item.block.renderer.BlockRenderer
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit

class NoteBlockRendererFactory : Factory<BlockRenderer, CustomBlock> {
    init {
        Bukkit.getPluginManager().registerEvents(NoteBlockRendererListener(), NekoProvider.neko().plugin())
    }

    override fun create(block: CustomBlock): BlockRenderer = NoteBlockRenderer(block)
}