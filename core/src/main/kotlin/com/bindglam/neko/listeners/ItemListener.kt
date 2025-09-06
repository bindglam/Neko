package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.utils.isReplaceable
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ItemListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun PlayerInteractEvent.tryUseCustomBlock() {
        clickedBlock ?: return
        if(action.isLeftClick) return

        NekoProvider.neko().contentManager().customBlock(clickedBlock!!) ?: return

        isCancelled = true
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun PlayerInteractEvent.tryPlaceBlockOnCustomBlock() {
        clickedBlock ?: return
        item ?: return
        if(action.isLeftClick) return
        if(!clickedBlock!!.type.isInteractable) return
        if(!item!!.type.isBlock) return

        NekoProvider.neko().contentManager().customBlock(clickedBlock!!) ?: return

        val location = if(!clickedBlock!!.type.isReplaceable) clickedBlock!!.getRelative(blockFace).location else clickedBlock!!.location

        if(player.canPlaceBlock(location)) {
            location.block.type = item!!.type
            player.swingHand(hand!!)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun PlayerInteractEvent.tryPlaceCustomBlock() {
        item ?: return
        hand ?: return
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK) return
        if (clickedBlock!!.isInteractable() && !player.isSneaking) return

        val customBlock = NekoProvider.neko().contentManager().customItem(item) ?: return
        if (customBlock !is CustomBlock) return

        val location = if(!clickedBlock!!.type.isReplaceable) clickedBlock!!.getRelative(blockFace).location else clickedBlock!!.location

        if(player.canPlaceBlock(location)) {
            customBlock.mechanism().place(location)
            player.swingHand(hand!!)

            isCancelled = true
        }
    }

    private fun Block.isInteractable(): Boolean = NekoProvider.neko().contentManager().customBlock(this) == null && type.isInteractable

    private fun Player.canPlaceBlock(location: Location): Boolean = location.block.type.isReplaceable && !location.block.boundingBox.contains(boundingBox)
}