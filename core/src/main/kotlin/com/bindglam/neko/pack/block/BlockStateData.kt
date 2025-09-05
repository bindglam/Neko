package com.bindglam.neko.pack.block

data class BlockStateData(
    val variants: MutableMap<String, Variant>
) {
    data class Variant(
        val model: String
    )
}