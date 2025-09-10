package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.glyph.GlyphProperties
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import org.bukkit.configuration.ConfigurationSection

class GlyphPropertiesConfigurable : Configurable<GlyphProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): GlyphProperties? = config?.let { GlyphProperties(
        KEY_CONFIGURABLE.load(config.getString("texture"))!!,
        config.getInt("offset-y"),
        config.getInt("scale")
    ) }
}