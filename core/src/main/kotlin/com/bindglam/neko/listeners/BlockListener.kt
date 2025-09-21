package com.bindglam.neko.listeners

import org.bukkit.Material
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun BlockPlaceEvent.placeOtherNoteBlock() {
        if(block.type == Material.NOTE_BLOCK) {
            val data = block.blockData as NoteBlock

            data.isPowered = false
        }
    }
}