package com.bindglam.neko.utils

import net.kyori.adventure.key.Key

val NULL_KEY = Key.key("neko", "null")

fun Key.toPackPath(ext: String): String = "assets/${namespace()}/${value()}.${ext}"