package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.sound.properties.Sound
import com.bindglam.neko.api.content.sound.properties.SoundEventProperties
import org.bukkit.configuration.ConfigurationSection

object SoundEventPropertiesConfigurable : Configurable<SoundEventProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): SoundEventProperties? = config?.let { SoundEventProperties.builder()
        .subtitle(config.getString("subtitle"))
        .sounds(config.getConfigurationSection("sounds")!!.getKeys(false).map { SoundConfigurable.load(config.getConfigurationSection("sounds.$it")!!) })
        .build()
    }

    private object SoundConfigurable : Configurable<Sound, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Sound? = config?.let { Sound.builder()
            .file(config.getString("file")!!)
            .volume(config.getDouble("volume", 1.0).toFloat())
            .pitch(config.getDouble("pitch", 1.0).toFloat())
            .weight(config.getInt("weight", 1))
            .stream(config.getBoolean("stream", true))
            .attenuationDistance(config.getInt("attenuation-distance", 16))
            .preload(config.getBoolean("preload", false))
            .build()
        }
    }
}