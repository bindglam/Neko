package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.content.glyph.GlyphProperties
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.pack.font.FontData
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import kotlin.math.abs

class ShiftGlyph : Glyph {
    companion object {
        val KEY = NamespacedKey(NekoProvider.neko().plugin(), "shift")

        private const val LEFT_CHARACTER = 'a'
        private const val RIGHT_CHARACTER = 'b'

        private val FONT_KEY = NamespacedKey(NekoProvider.neko().plugin(), "shift")
        private val FONT_FILE = NamespacedKey(NekoProvider.neko().plugin(), "font/shift")

        private val GSON = Gson()
    }

    override fun pack(zipper: PackZipper) {
        val path = FONT_FILE.toPackPath("json")

        val fontFile = zipper.file(path)
        val data = if(fontFile != null)
            GSON.fromJson(fontFile.bytes.get().decodeToString(), FontData::class.java)
        else
            FontData(arrayListOf())

        val advances = hashMapOf<Char, Int>()
        advances[LEFT_CHARACTER] = -1
        advances[RIGHT_CHARACTER] = 1

        data.providers.add(FontData.Space(advances))

        GSON.toJson(data).toByteArray().also {
            zipper.addFile(path, PackFile({ it }, it.size.toLong()))
        }
    }

    override fun component(builder: GlyphBuilder): Component = Component.text((if(builder.offsetX() < 0) LEFT_CHARACTER else RIGHT_CHARACTER).toString().repeat(builder.offsetX())).font(FONT_KEY)

    override fun key(): Key = FONT_KEY
    override fun properties(): GlyphProperties = GlyphProperties(Key.key("neko:null"), -32768, -10)
}