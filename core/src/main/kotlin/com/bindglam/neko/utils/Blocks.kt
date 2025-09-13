package com.bindglam.neko.utils

import com.bindglam.neko.api.NekoProvider
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.block.Block

private val REPLACEABLE_MATERIALS = Tag.REPLACEABLE.values

val Material.isReplaceable: Boolean
    get() = REPLACEABLE_MATERIALS.contains(this)

fun Block.isInteractable(): Boolean = NekoProvider.neko().contentManager().customBlock(this) == null && type.isInteractable