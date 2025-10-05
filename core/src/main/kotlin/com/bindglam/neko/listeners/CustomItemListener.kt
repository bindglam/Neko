package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.EventState
import com.bindglam.neko.utils.isInteractable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class CustomItemListener : Listener {
    @EventHandler(priority = EventPriority.LOW)
    fun PlayerInteractEvent.useCustomItem() {
        item ?: return
        if(!action.isRightClick) return
        if(useItemInHand() == Event.Result.DENY) return

        if(clickedBlock != null && clickedBlock!!.isInteractable() && NekoProvider.neko().contentManager().customBlock(clickedBlock) == null)
            return

        val customItem = NekoProvider.neko().contentManager().customItem(item) ?: return

        if(customItem.onUse(player, item!!) == EventState.CANCEL)
            isCancelled = true
    }
}