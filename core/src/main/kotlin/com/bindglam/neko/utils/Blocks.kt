package com.bindglam.neko.utils

import org.bukkit.Material
import org.bukkit.Tag

private val REPLACEABLE_MATERIALS = Tag.REPLACEABLE.values

val Material.isReplaceable: Boolean
    get() = REPLACEABLE_MATERIALS.contains(this)