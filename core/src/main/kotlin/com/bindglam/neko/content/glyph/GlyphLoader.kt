package com.bindglam.neko.content.glyph

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.content.GlyphPropertiesConfigurable
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

object GlyphLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        BuiltInRegistries.GLYPHS.register(key, Glyph(NamespacedKey(key.namespace(), key.value()), GlyphPropertiesConfigurable.load(config.getConfigurationSection("properties.glyph"))!!))
    }

    override fun id(): String = "glyph"
}