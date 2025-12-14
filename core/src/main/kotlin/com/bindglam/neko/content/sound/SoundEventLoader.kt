package com.bindglam.neko.content.sound

import com.bindglam.neko.api.content.ContentLoader
import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.sound.CustomSoundEvent
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.content.GlyphPropertiesConfigurable
import com.bindglam.neko.config.content.SoundEventPropertiesConfigurable
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection

object SoundEventLoader : ContentLoader {
    override fun load(key: Key, config: ConfigurationSection) {
        BuiltInRegistries.SOUNDS.register(key, CustomSoundEvent(NamespacedKey(key.namespace(), key.value()),
            SoundEventPropertiesConfigurable.load(config.getConfigurationSection("properties.sound"))!!))
    }

    override fun id(): String = "sound"
}