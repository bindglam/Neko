package com.bindglam.neko.pack

import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packable
import com.bindglam.neko.api.pack.minecraft.AtlasesMaker
import com.bindglam.neko.api.registry.BuiltInRegistries

object PackerApplier {
    fun apply(zipper: PackZipper) {
        BuiltInRegistries.ITEMS.entrySet().forEach { entry ->
            val item = entry.value

            if(item is Packable)
                item.pack(zipper)
        }

        BuiltInRegistries.BLOCKS.entrySet().forEach { entry ->
            val block = entry.value

            if(block is Packable)
                block.pack(zipper)
        }

        BuiltInRegistries.GLYPHS.entrySet().forEach { entry ->
            entry.value.pack(zipper)
        }

        BuiltInRegistries.FURNITURE.entrySet().forEach { entry ->
            val furniture = entry.value

            if(furniture is Packable)
                furniture.pack(zipper)
        }

        AtlasesMaker.pack(zipper)
    }
}