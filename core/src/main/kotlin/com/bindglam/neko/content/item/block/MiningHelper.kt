package com.bindglam.neko.content.item.block

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.utils.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object MiningHelper {
    private val progress = hashMapOf<UUID, Float>()
    private val lastUpdated = hashMapOf<UUID, Int>()

    private val tickTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(NekoProvider.neko().plugin(), { tick() }, 1L, 1L)

    private fun tick() {
        val tick = Bukkit.getCurrentTick()

        val forRemoval = arrayListOf<UUID>()
        lastUpdated.forEach { (uuid, lastTick) ->
            if(tick - lastTick > 2) {
                forRemoval.add(uuid)
            }
        }

        forRemoval.forEach {
            progress.remove(it)
            lastUpdated.remove(it)
        }
    }

    fun hasProgress(player: Player): Boolean = progress.contains(player.uniqueId)

    fun progress(player: Player): Float = progress.getOrDefault(player.uniqueId, 0f)

    fun updateProgress(player: Player, speed: Float) {
        if(!hasProgress(player))
            progress.put(player.uniqueId, 0f)

        progress[player.uniqueId] = progress(player) + speed
        lastUpdated[player.uniqueId] = Bukkit.getCurrentTick()
    }

    fun removeProgress(player: Player) {
        progress.remove(player.uniqueId)
    }
}