package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.mechanism.BlockMechanismFactory
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit

class NoteBlockMechanismFactory : BlockMechanismFactory {
    init {
        Bukkit.getPluginManager().registerEvents(NoteBlockMechanismListener(), NekoProvider.neko().plugin())
    }

    override fun create(block: CustomBlock): NoteBlockMechanism = NoteBlockMechanism(block)
}