package com.bindglam.neko.content.block

import com.bindglam.neko.utils.CURRENT_TICK
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object BlockHelper {
    private val breakProgress = hashMapOf<UUID, Float>()
    private val lastBreakProgressUpdated = hashMapOf<UUID, Int>()

    private val lastPlaceBlock = hashMapOf<UUID, Int>()

    fun tick() {
        val forRemoval = arrayListOf<UUID>()
        lastBreakProgressUpdated.forEach { (uuid, lastTick) ->
            if(CURRENT_TICK - lastTick > 2) {
                forRemoval.add(uuid)
            }
        }

        forRemoval.forEach {
            breakProgress.remove(it)
            lastBreakProgressUpdated.remove(it)
        }
    }

    fun hasBreakProgress(player: Player): Boolean = breakProgress.contains(player.uniqueId)

    fun breakProgress(player: Player): Float = breakProgress.getOrDefault(player.uniqueId, 0f)

    fun updateBreakProgress(player: Player, speed: Float) {
        if(!hasBreakProgress(player))
            breakProgress.put(player.uniqueId, 0f)

        breakProgress[player.uniqueId] = breakProgress(player) + speed
        lastBreakProgressUpdated[player.uniqueId] = CURRENT_TICK
    }

    fun removeBreakProgress(player: Player) {
        breakProgress.remove(player.uniqueId)
    }

    fun lastPlaceBlock(player: Player): Int = lastPlaceBlock.computeIfAbsent(player.uniqueId) { 0 }

    fun updateLastPlaceBlock(player: Player) {
        lastPlaceBlock[player.uniqueId] = CURRENT_TICK
    }
}