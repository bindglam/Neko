package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.api.registry.Holder
import com.bindglam.neko.api.registry.WritableRegistry
import com.bindglam.neko.utils.GLYPH_PROPERTIES_CONFIGURABLE
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

class GlyphLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        val registry = BuiltInRegistries.GLYPHS as WritableRegistry

        registry.register(key, Glyph(NamespacedKey(key.namespace(), key.value()), GLYPH_PROPERTIES_CONFIGURABLE.load(config.getConfigurationSection("properties.glyph"))!!))
    }

    override fun id(): String = "glyph"
}