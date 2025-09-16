package com.bindglam.neko.pack

import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.registry.BuiltInRegistries

object PackerApplier {
    fun apply(zipper: PackZipper) {
        BuiltInRegistries.ITEMS.entrySet().forEach { entry ->
            entry.value.pack(zipper)
        }

        BuiltInRegistries.GLYPHS.entrySet().forEach { entry ->
            entry.value.pack(zipper)
        }
    }
}