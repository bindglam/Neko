package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.EventState
import com.bindglam.neko.api.content.item.furniture.FurnitureItem
import com.bindglam.neko.api.event.FurnitureBreakEvent
import com.bindglam.neko.api.event.FurniturePlaceEvent
import com.bindglam.neko.content.furniture.FurnitureHelper
import com.bindglam.neko.utils.CURRENT_TICK
import com.bindglam.neko.utils.canPlaceBlock
import com.bindglam.neko.utils.isInteractable
import com.bindglam.neko.utils.isReplaceable
import com.bindglam.neko.utils.placeBlock
import com.bindglam.neko.utils.plugin
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.GameEvent
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Transformation
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.atan2
import kotlin.math.roundToInt

object CustomFurnitureListener : Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun PlayerInteractEvent.tryPlaceFurniture() {
        item ?: return
        hand ?: return
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK) return
        if (clickedBlock!!.isInteractable() && !player.isSneaking) return

        val customItem = NekoProvider.neko().contentManager().customItem(item) ?: return
        if (customItem !is FurnitureItem) return
        val furniture = customItem.furniture()

        val location = if(!clickedBlock!!.type.isReplaceable) clickedBlock!!.getRelative(blockFace).location else clickedBlock!!.location

        if(player.canPlaceBlock(location)) {
            isCancelled = true

            /*val placeSound = if(customFurniture.properties().sounds() != null)
                Sound.sound(customFurniture.properties().sounds()!!.placeSound(), Sound.Source.BLOCK, 1f, 1f)
            else
                Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f)*/

            if(FurniturePlaceEvent(player, furniture, location).callEvent()) {
                player.placeBlock(location, {
                    val dLoc = it.clone().subtract(player.location)
                    it.yaw = (Math.toDegrees(atan2(dLoc.z, dLoc.x)) / 90f).roundToInt() * 90f + 90f

                    furniture.place(it)
                }, clickedBlock!!, hand!!, Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f))

                FurnitureHelper.updateLastPlaceFurniture(player)
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerInteractEvent.tryBreakFurniture() {
        clickedBlock ?: return

        if (action != Action.LEFT_CLICK_BLOCK) return

        val furniture = NekoProvider.neko().contentManager().furniture(clickedBlock!!.location) ?: return

        val display = furniture.display(clickedBlock!!.location) ?: return
        display.display.interpolationDelay = 1
        display.display.interpolationDuration = 3
        display.applyTransformationModifier(Transformation(
            Vector3f(),
            Quaternionf(),
            Vector3f(-0.1f),
            Quaternionf()
        ))
        Bukkit.getScheduler().runTaskLater(NekoProvider.neko().plugin(), { _ ->
            val display = furniture.display(clickedBlock!!.location) ?: return@runTaskLater
            display.applyTransformationModifier(Transformation(
                Vector3f(),
                Quaternionf(),
                Vector3f(0.1f),
                Quaternionf()
            ))
        }, 4L)

        FurnitureHelper.updateBreakProgress(player, 1)

        isCancelled = true

        if(FurnitureHelper.breakProgress(player) >= 3) {
            FurnitureHelper.removeBreakProgress(player)

            if(FurnitureBreakEvent(player, furniture, clickedBlock!!.location).callEvent()
                    && BlockBreakEvent(clickedBlock!!, player).callEvent()) {
                furniture.destroy(clickedBlock!!.location)

                if(player.gameMode != GameMode.CREATIVE && furniture.item() != null) {
                    clickedBlock!!.world.dropItemNaturally(clickedBlock!!.location.toCenterLocation(), furniture.item()!!.itemStack())
                }

                clickedBlock!!.world.sendGameEvent(player, GameEvent.BLOCK_DESTROY, clickedBlock!!.location.toVector())
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerInteractEvent.interactFurniture() {
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK || player.isSneaking) return
        if(CURRENT_TICK - FurnitureHelper.lastPlaceFurniture(player) < 3) return

        val customFurniture = NekoProvider.neko().contentManager().furniture(clickedBlock!!.location) ?: return

        if(customFurniture.onInteract(player, clickedBlock!!.location, customFurniture.display(clickedBlock!!.location)) == EventState.CANCEL)
            isCancelled = true
    }
}