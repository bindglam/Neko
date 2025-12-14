package com.bindglam.neko.pack

import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker
import com.bindglam.neko.api.registry.BuiltInRegistries

object PackerApplier {
    fun apply(zipper: PackZipper) {
        BuiltInRegistries.ITEMS.forEach { it.pack(zipper) }
        BuiltInRegistries.BLOCKS.forEach { it.pack(zipper) }
        BuiltInRegistries.GLYPHS.forEach { it.pack(zipper) }
        BuiltInRegistries.FURNITURE.forEach { it.pack(zipper) }
        BuiltInRegistries.SOUNDS.forEach { it.pack(zipper) }

        AtlasesMaker.pack(zipper)
    }
}