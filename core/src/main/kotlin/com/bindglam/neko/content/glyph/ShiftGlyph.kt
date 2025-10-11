package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.content.glyph.properties.GlyphProperties
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.minecraft.font.FontData
import com.bindglam.neko.utils.NULL_KEY
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey

class ShiftGlyph : Glyph(SHIFT_GLYPH_KEY, GlyphProperties.builder().texture(NULL_KEY).offsetY(0).scale(0).build()) {
    companion object {
        private val CHARACTERS = linkedMapOf(
            'a' to -1, 'b' to -2, 'c' to -4, 'd' to -8, 'e' to -16, 'f' to -32, 'g' to -64, 'h' to -128,
            'i' to  1, 'j' to  2, 'k' to  4, 'l' to  8, 'm' to  16, 'n' to  32, 'o' to  64, 'v' to  128
        )

        private val GSON = Gson()

        private val FONT_FILE = NamespacedKey(NekoProvider.neko().plugin(), "font/shift")
    }

    override fun pack(zipper: PackZipper) {
        val path = FONT_FILE.toPackPath("json")

        val fontFile = zipper.file(path)
        val data = if(fontFile != null)
            GSON.fromJson(fontFile.bytes.get().decodeToString(), FontData::class.java)
        else
            FontData(arrayListOf())

        data.providers.add(FontData.Space(CHARACTERS))

        zipper.addFile(path, PackFile { GSON.toJson(data).toByteArray() })
    }

    override fun component(builder: GlyphBuilder): Component {
        val result = StringBuilder()

        var leftMove = builder.offsetX()

        val map = LinkedHashMap(CHARACTERS)
        for (i in 0..<CHARACTERS.size) {
            val entry = map.pollLastEntry()
            val ch = entry.key
            val move = entry.value

            if(leftMove == 0) break
            if (leftMove / move < 0) continue

            result.append(ch.toString().repeat(leftMove / move))
            leftMove -= move * (leftMove / move)
        }

        return Component.text(result.toString()).font(SHIFT_GLYPH_KEY)
    }
}