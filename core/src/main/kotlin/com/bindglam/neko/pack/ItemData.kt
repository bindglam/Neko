package com.bindglam.neko.pack

import com.google.gson.annotations.SerializedName

data class ItemData(
    val model: Model
) {
    data class Model(
        val type: Type,
        val model: String
    )

    enum class Type {
        @SerializedName("minecraft:model")
        MODEL
    }
}
