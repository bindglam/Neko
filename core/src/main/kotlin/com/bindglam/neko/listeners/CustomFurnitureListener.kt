package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.EventState
import com.bindglam.neko.api.content.item.furniture.FurnitureItem
import com.bindglam.neko.content.block.BlockHelper
import com.bindglam.neko.content.furniture.FurnitureHelper
import com.bindglam.neko.utils.canPlaceBlock
import com.bindglam.neko.utils.isInteractable
import com.bindglam.neko.utils.isReplaceable
import com.bindglam.neko.utils.placeBlock
import com.bindglam.neko.utils.plugin
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Transformation

class CustomFurnitureListener : Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun PlayerInteractEvent.tryPlaceCustomFurniture() {
        item ?: return
        hand ?: return
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK) return
        if (clickedBlock!!.isInteractable() && !player.isSneaking) return

        val customItem = NekoProvider.neko().contentManager().customItem(item) ?: return
        if (customItem !is FurnitureItem) return
        val customFurniture = customItem.furniture()

        val location = if(!clickedBlock!!.type.isReplaceable) clickedBlock!!.getRelative(blockFace).location else clickedBlock!!.location

        if(player.canPlaceBlock(location)) {
            isCancelled = true

            /*val placeSound = if(customFurniture.properties().sounds() != null)
                Sound.sound(customFurniture.properties().sounds()!!.placeSound(), Sound.Source.BLOCK, 1f, 1f)
            else
                Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f)*/

            player.placeBlock(location, { customFurniture.place(it) }, clickedBlock!!, hand!!, Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f))

            FurnitureHelper.updateLastPlaceFurniture(player)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerInteractEvent.tryBreakCustomFurniture() {
        clickedBlock ?: return

        if (action != Action.LEFT_CLICK_BLOCK) return

        val customFurniture = NekoProvider.neko().contentManager().furniture(clickedBlock!!.location) ?: return

        val display = customFurniture.display(clickedBlock!!.location) ?: return
        display.interpolationDelay = 1
        display.interpolationDuration = 3
        display.transformation = Transformation(
            display.transformation.translation,
            display.transformation.leftRotation,
            display.transformation.scale.sub(0.1f, 0.1f, 0.1f),
            display.transformation.rightRotation
        )
        Bukkit.getScheduler().runTaskLater(NekoProvider.neko().plugin(), { _ ->
            display.transformation = Transformation(
                display.transformation.translation,
                display.transformation.leftRotation,
                display.transformation.scale.add(0.1f, 0.1f, 0.1f),
                display.transformation.rightRotation
            )
        }, 4L)

        FurnitureHelper.updateBreakProgress(player, 1)

        isCancelled = true

        if(FurnitureHelper.breakProgress(player) >= 3) {
            customFurniture.destroy(clickedBlock!!.location)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerInteractEvent.interactCustomFurniture() {
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK || player.isSneaking) return
        if(Bukkit.getCurrentTick() - FurnitureHelper.lastPlaceFurniture(player) < 3) return

        val customFurniture = NekoProvider.neko().contentManager().furniture(clickedBlock!!.location) ?: return

        if(customFurniture.onInteract(player, clickedBlock!!.location) == EventState.CANCEL)
            isCancelled = true
    }
}