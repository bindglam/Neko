package com.bindglam.neko.compatibility.miniplaceholders

import com.bindglam.neko.api.content.glyph.Glyph
import com.bindglam.neko.api.content.glyph.GlyphBuilder
import com.bindglam.neko.api.registry.BuiltInRegistries
import io.github.miniplaceholders.api.Expansion
import io.github.miniplaceholders.api.utils.Tags
import net.kyori.adventure.text.minimessage.tag.Tag
import org.bukkit.NamespacedKey

object ShiftExpansion : ExpansionCreator {
    override fun create(builder: Expansion.Builder) {
        builder.globalPlaceholder("shift") { queue, ctx ->
            val amount = queue.pop().asInt().orElse(0)

            val wrapper = BuiltInRegistries.GLYPHS.get(Glyph.SHIFT_GLYPH_KEY)

            if(wrapper.isEmpty)
                return@globalPlaceholder Tags.EMPTY_TAG

            return@globalPlaceholder Tag.selfClosingInserting(wrapper.get().component(GlyphBuilder.builder().offsetX(amount)))
        }
    }
}