package com.bindglam.neko.utils

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

val NULL_KEY = NamespacedKey("neko", "null")

fun Key.toPackPath(ext: String): String = "assets/${namespace()}/${value()}.${ext}"