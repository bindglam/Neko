package com.bindglam.neko.utils

import com.bindglam.neko.api.NekoProvider
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.block.Block

private val REPLACEABLE_MATERIALS = Tag.REPLACEABLE.values

val Material.isReplaceable: Boolean
    get() = REPLACEABLE_MATERIALS.contains(this)

val Block.blastResistance: Float
    get() = NekoProvider.neko().contentManager().customBlock(this)?.properties()?.blastResistance() ?: type.blastResistance

val Material.explosionPower: Float
    get() = when(this) {
        Material.TNT -> 4f
        Material.RESPAWN_ANCHOR -> 5f
        else -> {
            if(name.contains("_BED")) 5f
            else 0f
        }
    }

fun Block.isInteractable(): Boolean = NekoProvider.neko().contentManager().customBlock(this) == null && type.isInteractable