package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.Sound

object SoundConfigurable : Configurable<Sound, String> {
    override fun load(config: String?): Sound? = config?.let { KeyConfigurable.load(config)?.let { p0 -> RegistryAccess.registryAccess().getRegistry(RegistryKey.SOUND_EVENT).get(p0) } }
}