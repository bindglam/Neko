package com.bindglam.neko.listeners

import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun BlockPlaceEvent.placeNoteBlock() {
        if(blockPlaced.type != Material.NOTE_BLOCK)
            return

        blockPlaced.blockData = (blockPlaced.blockData as NoteBlock).apply {
            instrument = Instrument.PIANO
            note = Note(0)
        }
    }
}