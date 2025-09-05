package com.bindglam.neko.utils

import org.bukkit.Location
import org.joml.Vector3i

private const val BITS_A = 10
private const val BITS_B = 12
private const val BITS_C = 10

private const val MASK_A = 1.shl(BITS_A) - 1
private const val MASK_B = 1.shl(BITS_B) - 1
private const val MASK_C = 1.shl(BITS_C) - 1

private val RANGE_A = IntRange(-(1.shl(BITS_A - 1)), 1.shl(BITS_A - 1) - 1)
private val RANGE_B = IntRange(-(1.shl(BITS_B - 1)), 1.shl(BITS_B - 1) - 1)
private val RANGE_C = IntRange(-(1.shl(BITS_C - 1)), 1.shl(BITS_C - 1) - 1)

fun Vector3i.pack(): Int {
    if(!RANGE_A.contains(x) || !RANGE_B.contains(y) || !RANGE_C.contains(z))
        throw IllegalArgumentException()

    var packed = 0
    packed = packed or ((x and MASK_A) shl (BITS_B + BITS_C))
    packed = packed or ((y and MASK_B) shl BITS_C)
    packed = packed or (z and MASK_C)
    return packed
}

fun Int.toVector3i() = Vector3i().apply {
    x = this@toVector3i shr (BITS_B + BITS_C)

    val yBits: Int = (this@toVector3i shr BITS_C) and MASK_B

    y = (yBits shl 22) shr 22

    val zBits: Int = this@toVector3i and MASK_C

    z = (zBits shl 20) shr 20
}

fun Location.toVector3i() = Vector3i(x.toInt(), y.toInt(), z.toInt())