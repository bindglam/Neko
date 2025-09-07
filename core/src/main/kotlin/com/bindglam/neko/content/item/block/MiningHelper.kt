package com.bindglam.neko.content.item.block

import com.github.benmanes.caffeine.cache.Caffeine
import org.bukkit.entity.Player
import java.time.Duration
import java.util.UUID

object MiningHelper {
    private val progress = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMillis(100))
        .build<UUID, Float>()

    fun hasProgress(player: Player): Boolean = progress.getIfPresent(player.uniqueId) != null

    fun progress(player: Player): Float = progress.getIfPresent(player.uniqueId)!!

    fun updateProgress(player: Player, speed: Float) {
        if(!hasProgress(player))
            progress.put(player.uniqueId, 0f)

        progress.put(player.uniqueId, progress(player) + speed)
    }

    fun removeProgress(player: Player) {
        progress.invalidate(player.uniqueId)
    }
}