package com.bindglam.neko.nms.v1_21_R5

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerPlayer
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

val Player.handle: ServerPlayer
    get() = (this as CraftPlayer).handle

fun Player.sendPacket(packet: List<Packet<*>>) {
    val connection = handle.connection
    packet.forEach { connection.send(it) }
}

fun BlockPos.toLocation(world: World?) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())