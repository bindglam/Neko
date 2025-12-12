package com.bindglam.neko.content.furniture

import com.bindglam.neko.utils.CURRENT_TICK
import org.bukkit.entity.Player
import java.util.*

object FurnitureHelper {
    private val breakProgress = hashMapOf<UUID, Int>()
    private val lastBreakProgressUpdated = hashMapOf<UUID, Int>()

    private val lastPlaceFurniture = hashMapOf<UUID, Int>()

    fun tick() {
        val forRemoval = arrayListOf<UUID>()
        lastBreakProgressUpdated.forEach { (uuid, lastTick) ->
            if(CURRENT_TICK - lastTick > 20) {
                forRemoval.add(uuid)
            }
        }

        forRemoval.forEach {
            breakProgress.remove(it)
            lastBreakProgressUpdated.remove(it)
        }
    }

    fun hasBreakProgress(player: Player): Boolean = breakProgress.contains(player.uniqueId)

    fun breakProgress(player: Player): Int = breakProgress.getOrDefault(player.uniqueId, 0)

    fun updateBreakProgress(player: Player, speed: Int) {
        if(!hasBreakProgress(player))
            breakProgress[player.uniqueId] = 0

        breakProgress[player.uniqueId] = breakProgress(player) + speed
        lastBreakProgressUpdated[player.uniqueId] = CURRENT_TICK
    }

    fun removeBreakProgress(player: Player) {
        breakProgress.remove(player.uniqueId)
    }

    fun lastPlaceFurniture(player: Player): Int = lastPlaceFurniture.computeIfAbsent(player.uniqueId) { 0 }

    fun updateLastPlaceFurniture(player: Player) {
        lastPlaceFurniture[player.uniqueId] = CURRENT_TICK
    }
}