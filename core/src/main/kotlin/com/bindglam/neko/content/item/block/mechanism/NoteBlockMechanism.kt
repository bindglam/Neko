package com.bindglam.neko.content.item.block.mechanism

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.content.item.block.mechanism.Mechanism
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packer
import com.bindglam.neko.pack.block.BlockStateData
import com.bindglam.neko.pack.block.BlockStateData.Variant
import com.bindglam.neko.pack.block.VanillaInstruments
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Note
import org.bukkit.block.BlockState
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.configuration.file.YamlConfiguration

class NoteBlockMechanism(private val customBlock: CustomBlock) : Mechanism {
    companion object {
        val KEY = NamespacedKey(NekoProvider.neko().plugin(), "note_block")

        private const val MAX_NOTE = 24
        private val BLOCKSTATE_FILE = Key.key("minecraft:blockstates/note_block").toPackPath("json")

        private val GSON = Gson()
    }

    private var instrument = VanillaInstruments.BANJO
    private var note = 0.toByte()

    init {
        loadData()
    }

    private fun loadData() {
        NekoProvider.neko().cacheManager().getCache("blocks.yml") ?: NekoProvider.neko().cacheManager().saveCache("blocks.yml") {}
        val blockCache = YamlConfiguration.loadConfiguration(NekoProvider.neko().cacheManager().getCache("blocks.yml")!!)

        if(blockCache.get("${customBlock.key().asString()}.instrument") == null) {
            instrument = VanillaInstruments.entries[blockCache.getInt("note-block.next-instrument")]
            note = blockCache.getInt("note-block.next-note").toByte()

            blockCache.set("${customBlock.key().asString()}.instrument", instrument.ordinal)
            blockCache.set("${customBlock.key().asString()}.note", note)

            if (note >= MAX_NOTE) {
                blockCache.set("note-block.next-instrument", instrument.ordinal + 1)
                blockCache.set("note-block.next-note", 0)

                if(VanillaInstruments.entries[instrument.ordinal + 1] == VanillaInstruments.HARP)
                    blockCache.set("note-block.next-note", 1)
            } else {
                blockCache.set("note-block.next-instrument", instrument.ordinal)
                blockCache.set("note-block.next-note", note + 1)
            }
        } else {
            instrument = VanillaInstruments.entries[blockCache.getInt("${customBlock.key().asString()}.instrument")]
            note = blockCache.getInt("${customBlock.key().asString()}.note").toByte()
        }

        NekoProvider.neko().cacheManager().saveCache("blocks.yml") { file -> blockCache.save(file) }
    }

    override fun pack(zipper: PackZipper, customBlock: CustomBlock) {
        val data = zipper.file(BLOCKSTATE_FILE)

        val blockStateData = if(data != null)
            GSON.fromJson(data.bytes.get().decodeToString(), BlockStateData::class.java)
        else
            BlockStateData(hashMapOf())

        blockStateData.variants["instrument=${instrument.name.lowercase()},note=${note}"] = Variant(customBlock.blockProperties().model().asString())

        GSON.toJson(blockStateData).toByteArray().also {
            zipper.addFile(BLOCKSTATE_FILE, PackFile({ it }, it.size.toLong()))
        }
    }

    override fun place(location: Location) {
        val block = location.block.apply { type = Material.NOTE_BLOCK }

        block.blockData = (block.blockData as NoteBlock).also {
            it.instrument = instrument.bukkit
            it.note = Note(note.toInt())
        }
    }

    override fun isSame(block: BlockState): Boolean {
        if(block.type != Material.NOTE_BLOCK)
            return false

        val data = block.blockData as NoteBlock

        return data.instrument == instrument.bukkit && data.note.id == note // TODO : Remove use of internal api
    }
}