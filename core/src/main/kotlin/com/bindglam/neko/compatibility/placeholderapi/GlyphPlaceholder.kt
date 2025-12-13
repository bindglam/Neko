package com.bindglam.neko.compatibility.placeholderapi

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.plugin
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer

object GlyphPlaceholder : PlaceholderExpansion() {
    override fun getIdentifier(): String = "glyph"

    override fun getAuthor(): String = "Bindglam"

    override fun getVersion(): String = NekoProvider.neko().plugin().pluginMeta.version

    override fun persist(): Boolean {
        return true
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        val args = params.split('*')
        val key = NamespacedKey.fromString(args[0])!!
        val offset = if(args.size == 2) args[1].toInt() else 0

        val wrapper = BuiltInRegistries.GLYPHS.get(key)

        if(wrapper.isEmpty)
            return null

        return MiniMessage.miniMessage().serialize(wrapper.get().component(GlyphBuilder.builder().offsetX(offset)))
    }
}