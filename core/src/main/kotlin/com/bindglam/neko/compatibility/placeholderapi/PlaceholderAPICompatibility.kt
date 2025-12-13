package com.bindglam.neko.compatibility.placeholderapi

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.compatibility.Compatibility
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit

object PlaceholderAPICompatibility : Compatibility {
    override val requiredPlugin: String
        get() = "PlaceholderAPI"

    override fun start() {
        Bukkit.getScheduler().runTask(NekoProvider.neko().plugin()) { _ ->
            listOf(GlyphPlaceholder)
                .forEach { it.register() }
        }
    }

    override fun end() {
    }
}