package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.glyph.GlyphProperties
import com.bindglam.neko.config.KeyConfigurable
import org.bukkit.configuration.ConfigurationSection

object GlyphPropertiesConfigurable : Configurable<GlyphProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): GlyphProperties? = config?.let { GlyphProperties.builder()
        .texture(KeyConfigurable.load(config.getString("texture"))!!)
        .offsetY(config.getInt("offset-y"))
        .scale(config.getInt("scale"))
        .build()
    }
}