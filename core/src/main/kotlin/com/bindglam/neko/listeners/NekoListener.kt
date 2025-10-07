package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.event.PackEvent
import com.bindglam.neko.utils.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object NekoListener : Listener {
    @EventHandler
    fun PackEvent.addDefaultAssets() {
        addPluginPack(NekoProvider.neko().plugin())
    }
}