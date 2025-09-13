package com.bindglam.neko.utils

import net.kyori.adventure.sound.Sound
import org.bukkit.GameEvent
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot
import java.util.function.Consumer

fun Player.canPlaceBlock(location: Location): Boolean = location.block.type.isReplaceable && !location.block.boundingBox.contains(boundingBox)

fun Player.placeBlock(location: Location, placeAction: Consumer<Location>, placedAgainst: Block, hand: EquipmentSlot, placeSound: Sound) {
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