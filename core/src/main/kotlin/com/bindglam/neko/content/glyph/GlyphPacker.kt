package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packer
import com.bindglam.neko.pack.font.FontData
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key

class GlyphPacker : Packer<Glyph> {
    companion object {
        private val GSON = Gson()
    }

    override fun pack(zipper: PackZipper, glyph: Glyph) {
        val path = Key.key(glyph.key.namespace(), "font/default").toPackPath("json")

        val fontFile = zipper.file(path)
        val data = if(fontFile != null)
            GSON.fromJson(fontFile.bytes.get().decodeToString(), FontData::class.java)
        else
            FontData(arrayListOf())

        // TODO : sprite sheet
        data.providers.add(FontData.Bitmap("${glyph.properties().texture().asString()}.png", glyph.properties().scale(), glyph.properties().offsetY(), listOf(glyph.character())))

        GSON.toJson(data).toByteArray().also {
            zipper.addFile(path, PackFile({ it }, it.size.toLong()))
        }
    }
}