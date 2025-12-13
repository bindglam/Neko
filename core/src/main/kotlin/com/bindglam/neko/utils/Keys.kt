package com.bindglam.neko.utils

import com.bindglam.neko.api.NekoProvider
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

val NULL_KEY = NamespacedKey("neko", "null")

fun neko(value: String): NamespacedKey = NamespacedKey(NekoProvider.neko().plugin(), value)

fun Key.toPackPath(ext: String): String = "assets/${namespace()}/${value()}.${ext}"