package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.utils.isReplaceable
import net.kyori.adventure.sound.Sound
import org.bukkit.GameEvent
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.function.Consumer

class ItemListener : Listener {
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
            isCancelled = true

            player.placeBlock(location, { it.block.type = item!!.type }, clickedBlock!!, hand!!, Sound.sound(item!!.type.createBlockData().soundGroup.placeSound, Sound.Source.BLOCK, 1f, 1f))
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
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
            isCancelled = true

            val placeSound = if(customBlock.blockProperties().sounds() != null && customBlock.blockProperties().sounds()!!.placeSound() != null)
                Sound.sound(customBlock.blockProperties().sounds()!!.placeSound()!!, Sound.Source.BLOCK, 1f, 1f)
            else
                Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f)

            player.placeBlock(location, { customBlock.mechanism().place(it) }, clickedBlock!!, hand!!, placeSound)
        }
    }

    private fun Player.placeBlock(location: Location, placeAction: Consumer<Location>, placedAgainst: Block, hand: EquipmentSlot, placeSound: Sound) {
        val item = inventory.getItem(hand)

        val prevBlockData = location.block.blockData.clone()

        placeAction.accept(location)
        swingHand(hand)

        val event = BlockPlaceEvent(location.block, location.block.state, placedAgainst, item, this, true, hand)

        if(!event.callEvent() || !event.canBuild()) {
            location.block.blockData = prevBlockData
            return
        }

        playSound(placeSound)

        if(gameMode != GameMode.CREATIVE)
            item.amount -= 1

        location.world.sendGameEvent(player, GameEvent.BLOCK_PLACE, location.toVector())
    }

    private fun Block.isInteractable(): Boolean = NekoProvider.neko().contentManager().customBlock(this) == null && type.isInteractable

    private fun Player.canPlaceBlock(location: Location): Boolean = location.block.type.isReplaceable && !location.block.boundingBox.contains(boundingBox)
}