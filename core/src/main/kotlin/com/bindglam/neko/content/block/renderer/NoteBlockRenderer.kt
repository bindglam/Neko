package com.bindglam.neko.content.block.renderer

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.block.Block
import com.bindglam.neko.api.content.block.renderer.BlockRenderer
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packable
import com.bindglam.neko.api.pack.minecraft.block.BlockStateData
import com.bindglam.neko.api.pack.minecraft.block.VanillaInstruments
import com.bindglam.neko.api.utils.GsonUtils
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Note
import org.bukkit.block.BlockState
import org.bukkit.block.BlockType
import org.bukkit.block.data.type.NoteBlock

class NoteBlockRenderer(private val block: Block) : BlockRenderer, Packable {
    companion object {
        val KEY = NamespacedKey(NekoProvider.neko().plugin(), "note_block")

        private const val MAX_NOTE = 24
        private val BLOCKSTATE_FILE = Key.key("minecraft:blockstates/note_block").toPackPath("json")
    }

    private var instrument = VanillaInstruments.BANJO
    private var note = 0.toByte()
    private var powered = false

    init {
        loadData()
    }

    private fun loadData() {
        val blockCache = NekoProvider.neko().cacheManager().getCache("blocks")

        if(blockCache["${block.key().asString()}.instrument"] == null) {
            instrument = VanillaInstruments.entries[blockCache.getInteger("note-block.next-instrument", 0)]
            note = blockCache.getByte("note-block.next-note", 0)
            powered = if(instrument == VanillaInstruments.HARP) true else blockCache.getBoolean("note-block.next-powered", false)

            blockCache["${block.key().asString()}.instrument"] = instrument.ordinal
            blockCache["${block.key().asString()}.note"] = note
            blockCache["${block.key().asString()}.powered"] = powered

            if (note >= MAX_NOTE) {
                blockCache["note-block.next-instrument"] = instrument.ordinal + 1
                blockCache["note-block.next-note"] = 0
                blockCache["note-block.next-powered"] = VanillaInstruments.entries[instrument.ordinal + 1] == VanillaInstruments.HARP
            } else if(powered || instrument == VanillaInstruments.HARP) {
                blockCache["note-block.next-instrument"] = instrument.ordinal
                blockCache["note-block.next-note"] = note + 1
                blockCache["note-block.next-powered"] = instrument == VanillaInstruments.HARP
            } else {
                blockCache["note-block.next-instrument"] = instrument.ordinal
                blockCache["note-block.next-note"] = note
                blockCache["note-block.next-powered"] = true
            }
        } else {
            instrument = VanillaInstruments.entries[blockCache.getInteger("${block.key().asString()}.instrument", 0)]
            note = blockCache.getByte("${block.key().asString()}.note", 0)
            powered = blockCache.getBoolean("${block.key().asString()}.powered", false)
        }
    }

    override fun pack(zipper: PackZipper) {
        val data = zipper.file(BLOCKSTATE_FILE)

        val blockStateData = if(data != null)
            GsonUtils.GSON.fromJson(data.bytes.get().decodeToString(), BlockStateData::class.java)
        else
            BlockStateData(hashMapOf())

        blockStateData.variants["instrument=${instrument.name.lowercase()},note=${note},powered=${powered}"] = BlockStateData.Variant(block.properties().model().asString())

        zipper.addFile(BLOCKSTATE_FILE, PackFile { GsonUtils.GSON.toJson(blockStateData).toByteArray() })
    }

    override fun createBlockState(): BlockState = BlockType.NOTE_BLOCK.createBlockData().also {
        it.instrument = instrument.bukkit
        it.note = Note(note.toInt())
        it.isPowered = powered
    }.createBlockState()

    override fun isSame(block: BlockState): Boolean {
        if(block.type != Material.NOTE_BLOCK)
            return false

        val data = block.blockData as NoteBlock

        return data.instrument == instrument.bukkit && data.note.id == note // TODO : Remove use of internal api
    }
}