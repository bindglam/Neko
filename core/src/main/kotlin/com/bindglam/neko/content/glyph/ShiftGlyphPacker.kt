package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packer
import com.bindglam.neko.pack.font.FontData
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import org.bukkit.NamespacedKey
import kotlin.text.decodeToString

class ShiftGlyphPacker : Packer<ShiftGlyph> {
    companion object {
        private val GSON = Gson()

        private val FONT_FILE = NamespacedKey(NekoProvider.neko().plugin(), "font/shift")
    }

    override fun pack(zipper: PackZipper, glyph: ShiftGlyph) {
        val path = FONT_FILE.toPackPath("json")

        val fontFile = zipper.file(path)
        val data = if(fontFile != null)
            GSON.fromJson(fontFile.bytes.get().decodeToString(), FontData::class.java)
        else
            FontData(arrayListOf())

        data.providers.add(FontData.Space(ShiftGlyph.CHARACTERS))

        GSON.toJson(data).toByteArray().also {
            zipper.addFile(path, PackFile({ it }, it.size.toLong()))
        }
    }
}