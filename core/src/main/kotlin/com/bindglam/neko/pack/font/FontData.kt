package com.bindglam.neko.pack.font

import com.google.gson.annotations.SerializedName

data class FontData(
    val providers: MutableList<Provider>
) {
    open class Provider(private val type: Type) {
        enum class Type {
            @SerializedName("bitmap")
            BITMAP,

            @SerializedName("space")
            SPACE,
        }
    }

    data class Bitmap(
        val file: String,
        val height: Int,
        val ascent: Int,
        val chars: List<Char>
    ) : Provider(Type.BITMAP)

    data class Space(
        val advances: MutableMap<Char, Int>
    ) : Provider(Type.SPACE)
}
