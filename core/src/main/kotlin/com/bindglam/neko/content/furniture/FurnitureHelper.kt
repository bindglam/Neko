package com.bindglam.neko.content.furniture

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object FurnitureHelper {
    private val breakProgress = hashMapOf<UUID, Int>()
    private val lastBreakProgressUpdated = hashMapOf<UUID, Int>()

    private val lastPlaceFurniture = hashMapOf<UUID, Int>()

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NekoProvider.neko().plugin(), { tick() }, 1L, 1L)
    }

    private fun tick() {
        val tick = Bukkit.getCurrentTick()

        val forRemoval = arrayListOf<UUID>()
        lastBreakProgressUpdated.forEach { (uuid, lastTick) ->
            if(tick - lastTick > 20) {
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
            breakProgress.put(player.uniqueId, 0)

        breakProgress[player.uniqueId] = breakProgress(player) + speed
        lastBreakProgressUpdated[player.uniqueId] = Bukkit.getCurrentTick()
    }

    fun removeBreakProgress(player: Player) {
        breakProgress.remove(player.uniqueId)
    }

    fun lastPlaceFurniture(player: Player): Int = lastPlaceFurniture.computeIfAbsent(player.uniqueId) { 0 }

    fun updateLastPlaceFurniture(player: Player) {
        lastPlaceFurniture[player.uniqueId] = Bukkit.getCurrentTick()
    }
}