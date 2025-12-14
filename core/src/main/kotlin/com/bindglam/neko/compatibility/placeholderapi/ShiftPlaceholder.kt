package com.bindglam.neko.compatibility.placeholderapi

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.plugin
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.OfflinePlayer

object ShiftPlaceholder : PlaceholderExpansion() {
    override fun getIdentifier(): String = "shift"

    override fun getAuthor(): String = "Bindglam"

    override fun getVersion(): String = NekoProvider.neko().plugin().pluginMeta.version

    override fun persist(): Boolean {
        return true
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        val offset = params.toInt()

        val wrapper = BuiltInRegistries.GLYPHS.get(Glyph.SHIFT_GLYPH_KEY)

        if(wrapper.isEmpty)
            return null

        return "<reset>" + MiniMessage.miniMessage().serialize(wrapper.get().component(GlyphBuilder.builder().offsetX(offset))) + "<reset>"
    }
}