package com.bindglam.neko.utils

import net.kyori.adventure.key.Key

fun Key.toPackPath(ext: String): String = "assets/${namespace()}/${value()}.${ext}"