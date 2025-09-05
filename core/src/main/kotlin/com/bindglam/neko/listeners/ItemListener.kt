package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ItemListener : Listener {
    @EventHandler
    fun PlayerInteractEvent.tryPlaceCustomBlock() {
        item ?: return
        interactionPoint ?: return

        val customItem = NekoProvider.neko().contentManager().customItem(item!!) ?: return

        if(customItem is CustomBlock) {
            customItem.mechanism().place(interactionPoint!!)

            player.swingMainHand()
        }
    }
}