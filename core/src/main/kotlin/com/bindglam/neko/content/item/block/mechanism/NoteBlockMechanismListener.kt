package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.NekoProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.NotePlayEvent

class NoteBlockMechanismListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun NotePlayEvent.cancelCustomBlockInteract() {
        NekoProvider.neko().contentManager().customBlock(block) ?: return

        isCancelled = true
    }
}