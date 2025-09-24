package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ItemListener : Listener {
    @EventHandler(priority = EventPriority.LOW)
    fun PlayerInteractEvent.useCustomItem() {
        item ?: return
        if(useItemInHand() == Event.Result.DENY) return

        val customItem = NekoProvider.neko().contentManager().customItem(item) ?: return

        if(action.isRightClick)
            customItem.onUse(player, item!!)
    }
}