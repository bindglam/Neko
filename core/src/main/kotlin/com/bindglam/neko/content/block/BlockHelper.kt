package com.bindglam.neko.content.block

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object BlockHelper {
    private val breakProgress = hashMapOf<UUID, Float>()
    private val lastBreakProgressUpdated = hashMapOf<UUID, Int>()

    private val lastPlaceBlock = hashMapOf<UUID, Int>()

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NekoProvider.neko().plugin(), { tick() }, 1L, 1L)
    }

    private fun tick() {
        val tick = Bukkit.getCurrentTick()

        val forRemoval = arrayListOf<UUID>()
        lastBreakProgressUpdated.forEach { (uuid, lastTick) ->
            if(tick - lastTick > 2) {
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
        lastBreakProgressUpdated[player.uniqueId] = Bukkit.getCurrentTick()
    }

    fun removeBreakProgress(player: Player) {
        breakProgress.remove(player.uniqueId)
    }

    fun lastPlaceBlock(player: Player): Int = lastPlaceBlock.computeIfAbsent(player.uniqueId) { 0 }

    fun updateLastPlaceBlock(player: Player) {
        lastPlaceBlock[player.uniqueId] = Bukkit.getCurrentTick()
    }
}