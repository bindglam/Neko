package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.PlayerNetworkManager
import com.bindglam.neko.api.nms.PlayerChannelHandler
import com.bindglam.neko.nms.PlayerChannelHandlerImpl
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object PlayerNetworkManagerImpl : PlayerNetworkManager {
    private val channelHandlers = ConcurrentHashMap<UUID, PlayerChannelHandler>()

    override fun start() {
    }

    override fun end() {
    }

    override fun inject(player: Player) {
        channelHandlers.computeIfAbsent(player.uniqueId) { PlayerChannelHandlerImpl(player) }
    }

    override fun eject(player: Player) {
        channelHandlers.remove(player.uniqueId)?.close()
    }
}