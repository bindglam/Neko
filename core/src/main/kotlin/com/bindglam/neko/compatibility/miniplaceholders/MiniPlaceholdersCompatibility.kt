package com.bindglam.neko.compatibility.miniplaceholders

import com.bindglam.neko.compatibility.Compatibility
import io.github.miniplaceholders.api.Expansion

object MiniPlaceholdersCompatibility : Compatibility {
    override val requiredPlugin: String
        get() = "MiniPlaceholders"

    private val creators = listOf(GlyphExpansion, ShiftExpansion)

    override fun start() {
        val builder = Expansion.builder("neko")

        creators.forEach { it.create(builder) }

        builder.build().register()
    }

    override fun end() {
    }
}