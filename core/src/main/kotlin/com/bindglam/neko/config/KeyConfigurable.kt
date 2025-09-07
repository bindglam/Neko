package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import net.kyori.adventure.key.Key

class KeyConfigurable : Configurable<Key, String> {
    override fun load(value: String?): Key? = value?.let { Key.key(value) }
}