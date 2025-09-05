package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerJoinQuitListener : Listener {
    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        NekoProvider.neko().playerNetworkManager().inject(player)
    }

    @EventHandler
    fun PlayerQuitEvent.onJoin() {
        NekoProvider.neko().playerNetworkManager().eject(player)
    }
}