package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.content.glyph.GlyphProperties
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.pack.font.FontData
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.YamlConfiguration
import kotlin.text.decodeToString

class GlyphImpl(private val key: Key, private val properties: GlyphProperties) : Glyph {
    companion object {
        private val GSON = Gson()
    }

    private val fontKey = Key.key(key.namespace(), "default")

    private var character: Char = 'a'

    init {
        loadData()
    }

    fun loadData() {
        NekoProvider.neko().cacheManager().getCache("glyphs.yml") ?: NekoProvider.neko().cacheManager().saveCache("glyphs.yml") {}
        val glyphCache = YamlConfiguration.loadConfiguration(NekoProvider.neko().cacheManager().getCache("glyphs.yml")!!)

        if(glyphCache.get("${key.asString()}.char") == null) {
            character = glyphCache.getString("glyph.next-char", "a")!![0]

            glyphCache.set("${key.asString()}.char", character)

            glyphCache.set("glyph.next-char", character+1)
        } else {
            character = glyphCache.getString("${key.asString()}.char")!![0]
        }

        NekoProvider.neko().cacheManager().saveCache("glyphs.yml") { file -> glyphCache.save(file) }
    }

    override fun pack(zipper: PackZipper) {
        val path = Key.key(key.namespace(), "font/default").toPackPath("json")

        val fontFile = zipper.file(path)
        val data = if(fontFile != null)
            GSON.fromJson(fontFile.bytes.get().decodeToString(), FontData::class.java)
        else
            FontData(arrayListOf())

        // TODO : sprite sheet
        data.providers.add(FontData.Bitmap("${properties.texture().asString()}.png", properties.scale(), properties.offsetY(), listOf(character)))

        GSON.toJson(data).toByteArray().also {
            zipper.addFile(path, PackFile({ it }, it.size.toLong()))
        }
    }

    override fun component(builder: GlyphBuilder): Component = BuiltInRegistries.GLYPHS.get(Glyph.SHIFT_GLYPH_KEY).component(builder).append(Component.text(character).font(fontKey))

    override fun key(): Key = key
    override fun properties(): GlyphProperties = properties
}