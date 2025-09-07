package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class ComponentConfigurable : Configurable<Component, String> {
    override fun load(value: String?): Component? = value?.let { MiniMessage.miniMessage().deserialize(it) }
}