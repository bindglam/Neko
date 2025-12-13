package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.manager.LifecycleContext
import com.bindglam.neko.api.manager.PlayerNetworkManager
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.nms.PlayerChannelHandler
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object PlayerNetworkManagerImpl : PlayerNetworkManager {
    private val channelHandlers = ConcurrentHashMap<UUID, PlayerChannelHandler>()

    override fun start(context: LifecycleContext, process: Process) {
    }

    override fun end(context: LifecycleContext, process: Process) {
    }

    override fun inject(player: Player) {
        channelHandlers.computeIfAbsent(player.uniqueId) { NekoProvider.neko().nms().createChannelHandler(player) }
    }

    override fun eject(player: Player) {
        channelHandlers.remove(player.uniqueId)?.close()
    }
}