package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.utils.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerListener : Listener {
    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        NekoProvider.neko().playerNetworkManager().inject(player)

        NekoProvider.neko().packManager().sendPack(player)
    }

    @EventHandler
    fun PlayerQuitEvent.onQuit() {
        NekoProvider.neko().playerNetworkManager().eject(player)
    }
}