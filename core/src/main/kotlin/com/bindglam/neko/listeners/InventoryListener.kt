package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class InventoryListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun PrepareItemCraftEvent.cancelCraftItemsWithCustomItems() {
        recipe ?: return
        if(recipe !is Keyed) return

        val key = (recipe as Keyed).key()

        val useCustomItem = inventory.matrix.any { it != null && NekoProvider.neko().contentManager().customItem(it) != null }
        if(!useCustomItem) return

        if(key.namespace() == Key.MINECRAFT_NAMESPACE)
            inventory.result = null
    }
}