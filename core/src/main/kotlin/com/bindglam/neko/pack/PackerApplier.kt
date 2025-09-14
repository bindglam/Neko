package com.bindglam.neko.pack

import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.content.glyph.GlyphPacker
import com.bindglam.neko.content.glyph.ShiftGlyph
import com.bindglam.neko.content.glyph.ShiftGlyphPacker
import com.bindglam.neko.content.item.CustomItemPacker

object PackerApplier {
    private val CUSTOM_ITEM_PACKER = CustomItemPacker()
    private val GLYPH_PACKER = GlyphPacker()
    private val SHIFT_GLYPH_PACKER = ShiftGlyphPacker()

    fun apply(zipper: PackZipper) {
        BuiltInRegistries.ITEMS.entrySet().forEach { entry ->
            CUSTOM_ITEM_PACKER.pack(zipper, entry.value)
        }

        BuiltInRegistries.BLOCKS.entrySet().forEach { entry ->
            entry.value.mechanism().pack(zipper, entry.value)
        }

        BuiltInRegistries.GLYPHS.entrySet().forEach { entry ->
            if(entry.value is ShiftGlyph)
                SHIFT_GLYPH_PACKER.pack(zipper, entry.value as ShiftGlyph)
            else
                GLYPH_PACKER.pack(zipper, entry.value)
        }
    }
}