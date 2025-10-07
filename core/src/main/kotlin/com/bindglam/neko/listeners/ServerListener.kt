package com.bindglam.neko.listeners

import com.bindglam.neko.content.block.BlockHelper
import com.bindglam.neko.content.furniture.FurnitureHelper
import com.bindglam.neko.utils.CURRENT_TICK
import com.destroystokyo.paper.event.server.ServerTickStartEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object ServerListener : Listener {
    @EventHandler
    fun ServerTickStartEvent.tickHelpers() {
        CURRENT_TICK = tickNumber

        BlockHelper.tick()
        FurnitureHelper.tick()
    }
}