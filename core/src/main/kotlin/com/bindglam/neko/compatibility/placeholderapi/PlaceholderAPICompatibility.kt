package com.bindglam.neko.compatibility.placeholderapi

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.compatibility.Compatibility

object PlaceholderAPICompatibility : Compatibility {
    override val requiredPlugin: String
        get() = "PlaceholderAPI"

    override fun start() {
        NekoProvider.neko().scheduler().run {
            listOf(GlyphPlaceholder, ShiftPlaceholder)
                .forEach { it.register() }
        }
    }

    override fun end() {
    }
}