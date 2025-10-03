package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.Sound

class SoundConfigurable : Configurable<Sound, String> {
    override fun load(config: String?): Sound? = config?.let { KEY_CONFIGURABLE.load(config)?.let { p0 -> RegistryAccess.registryAccess().getRegistry(RegistryKey.SOUND_EVENT).get(p0) } }
}