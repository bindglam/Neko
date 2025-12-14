package com.bindglam.neko.compatibility.miniplaceholders

import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.registry.BuiltInRegistries
import io.github.miniplaceholders.api.Expansion
import io.github.miniplaceholders.api.utils.Tags
import net.kyori.adventure.text.minimessage.tag.Tag
import org.bukkit.NamespacedKey

object GlyphExpansion : ExpansionCreator {
    override fun create(builder: Expansion.Builder) {
        builder.globalPlaceholder("glyph") { queue, ctx ->
            val key = NamespacedKey.fromString(queue.pop().value().replace(".", ":"))!!

            val wrapper = BuiltInRegistries.GLYPHS.get(key)

            if(wrapper.isEmpty)
                return@globalPlaceholder Tags.EMPTY_TAG

            return@globalPlaceholder Tag.selfClosingInserting(wrapper.get().component(GlyphBuilder.builder()))
        }
    }
}