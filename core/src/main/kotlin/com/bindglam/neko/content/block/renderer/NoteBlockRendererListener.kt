package com.bindglam.neko.content.block.renderer

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.pack.minecraft.block.VanillaInstruments
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.Tag
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.NotePlayEvent

class NoteBlockRendererListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun BlockPlaceEvent.placeOtherNoteBlock() {
        if(block.type == Material.NOTE_BLOCK) {
            val data = block.blockData as NoteBlock

            data.isPowered = false
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun NotePlayEvent.cancelCustomBlockInteract() {
        val customBlock = NekoProvider.neko().contentManager().customBlock(block)

        if(customBlock != null) {
            isCancelled = true
        } else {
            val belowBlock = block.world.getBlockAt(block.location.add(0.0, -1.0, 0.0))

            instrument = getInstrument(belowBlock)

            block.blockData = (block.blockData as NoteBlock).also {
                it.instrument = VanillaInstruments.HARP.bukkit
                it.note = if(it.note.id+1 > 24) Note(0) else Note(it.note.id+1)
                it.isPowered = false
            }
        }
    }

    private fun getInstrument(belowBlock: Block): Instrument {
        return if(Tag.OAK_LOGS.isTagged(belowBlock.type))
            Instrument.BASS_GUITAR
        else if(belowBlock.type == Material.SAND || belowBlock.type == Material.GRAVEL)
            Instrument.SNARE_DRUM
        else if(belowBlock.type == Material.GLASS)
            Instrument.STICKS
        else if(belowBlock.type == Material.STONE)
            Instrument.BASS_DRUM
        else if(belowBlock.type == Material.GOLD_BLOCK)
            Instrument.BELL
        else if(belowBlock.type == Material.CLAY)
            Instrument.FLUTE
        else if(belowBlock.type == Material.PACKED_ICE)
            Instrument.CHIME
        else if(Tag.WOOL.isTagged(belowBlock.type))
            Instrument.GUITAR
        else if(belowBlock.type == Material.BONE_BLOCK)
            Instrument.XYLOPHONE
        else if(belowBlock.type == Material.IRON_BLOCK)
            Instrument.IRON_XYLOPHONE
        else if(belowBlock.type == Material.SOUL_SAND)
            Instrument.COW_BELL
        else if(belowBlock.type == Material.PUMPKIN)
            Instrument.DIDGERIDOO
        else if(belowBlock.type == Material.EMERALD_BLOCK)
            Instrument.BIT
        else if(belowBlock.type == Material.HAY_BLOCK)
            Instrument.BANJO
        else if(belowBlock.type == Material.GLOWSTONE)
            Instrument.PLING
        else
            Instrument.PIANO
    }
}