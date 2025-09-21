package com.bindglam.neko.content.item.block.renderer

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.pack.minecraft.block.VanillaInstruments
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.Tag
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.NotePlayEvent

class NoteBlockRendererListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun NotePlayEvent.cancelCustomBlockInteract() {
        val customBlock = NekoProvider.neko().contentManager().customBlock(block)

        if(customBlock != null) {
            isCancelled = true
        } else {
            val belowBlock = block.world.getBlockAt(block.location.add(0.0, -1.0, 0.0))

            if(Tag.OAK_LOGS.isTagged(belowBlock.type))
                instrument = Instrument.BASS_GUITAR
            else if(belowBlock.type == Material.SAND || belowBlock.type == Material.GRAVEL)
                instrument = Instrument.SNARE_DRUM
            else if(belowBlock.type == Material.GLASS)
                instrument = Instrument.STICKS
            else if(belowBlock.type == Material.STONE)
                instrument = Instrument.BASS_DRUM
            else if(belowBlock.type == Material.GOLD_BLOCK)
                instrument = Instrument.BELL
            else if(belowBlock.type == Material.CLAY)
                instrument = Instrument.FLUTE
            else if(belowBlock.type == Material.PACKED_ICE)
                instrument = Instrument.CHIME
            else if(Tag.WOOL.isTagged(belowBlock.type))
                instrument = Instrument.GUITAR
            else if(belowBlock.type == Material.BONE_BLOCK)
                instrument = Instrument.XYLOPHONE
            else if(belowBlock.type == Material.IRON_BLOCK)
                instrument = Instrument.IRON_XYLOPHONE
            else if(belowBlock.type == Material.SOUL_SAND)
                instrument = Instrument.COW_BELL
            else if(belowBlock.type == Material.PUMPKIN)
                instrument = Instrument.DIDGERIDOO
            else if(belowBlock.type == Material.EMERALD_BLOCK)
                instrument = Instrument.BIT
            else if(belowBlock.type == Material.HAY_BLOCK)
                instrument = Instrument.BANJO
            else if(belowBlock.type == Material.GLOWSTONE)
                instrument = Instrument.PLING

            block.blockData = (block.blockData as NoteBlock).also {
                it.instrument = VanillaInstruments.HARP.bukkit
                it.note = if(it.note.id+1 > 24) Note(0) else Note(it.note.id+1)
                it.isPowered = false
            }
        }
    }
}