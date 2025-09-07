package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.content.item.block.MiningHelper
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.event.player.PlayerArmSwingEvent
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import org.bukkit.FluidCollisionMode
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Tag
import org.bukkit.block.Block
import org.bukkit.entity.ExperienceOrb
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerAnimationEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerListener : Listener {
    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        NekoProvider.neko().playerNetworkManager().inject(player)
    }

    @EventHandler
    fun PlayerQuitEvent.onQuit() {
        NekoProvider.neko().playerNetworkManager().eject(player)
    }

    @EventHandler
    fun PlayerAnimationEvent.tryBreakCustomBlock() {
        if (player.gameMode != GameMode.SURVIVAL) return

        val block = player.getTargetBlockExact(3, FluidCollisionMode.NEVER) ?: return
        val customBlock = NekoProvider.neko().contentManager().customBlock(block) ?: return

        val blockBreakSpeedData = blockBreakSpeed(player, customBlock)

        player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, 5, Int.MAX_VALUE, false, false, false))

        MiningHelper.updateProgress(player, blockBreakSpeedData.speed)
        player.sendBlockDamage(block.location, MiningHelper.progress(player).coerceAtMost(1f))

        if(MiningHelper.progress(player) >= 1f) {
            MiningHelper.removeProgress(player)

            val event = BlockBreakEvent(block, player)
            event.callEvent()

            if(!event.isCancelled) {
                Particle.BLOCK.builder()
                    .location(block.location)
                    .offset(0.5, 0.5, 0.5)
                    .count(40)
                    .extra(10.0)
                    .receivers(32, true)
                    .data(block.blockData.clone())
                    .spawn()
                block.type = Material.AIR

                if(blockBreakSpeedData.isCorrectTool) {
                    dropItems(block, customBlock)
                }
            }
        }
    }

    data class BlockBreakSpeedData(
        val speed: Float,
        val isCorrectTool: Boolean
    )

    private fun blockBreakSpeed(player: Player, customBlock: CustomBlock): BlockBreakSpeedData {
        var itemBlockBreakSpeed = 1.0f
        var isCorrectTool = customBlock.blockProperties().correctTools()?.isCorrectTool(player.inventory.itemInMainHand) == true

        if(player.inventory.itemInMainHand.hasData(DataComponentTypes.TOOL)) {
            player.inventory.itemInMainHand.getData(DataComponentTypes.TOOL)!!.also { tool ->
                itemBlockBreakSpeed = tool.defaultMiningSpeed()

                if(isCorrectTool) {
                    tool.rules().forEach {
                        it.speed() ?: return@forEach

                        if(itemBlockBreakSpeed < it.speed()!!)
                            itemBlockBreakSpeed = it.speed()!!
                    }
                }
            }
        }

        val blockBreakSpeed = itemBlockBreakSpeed / customBlock.blockProperties().hardness / if(isCorrectTool) 30f else 100f

        return BlockBreakSpeedData(blockBreakSpeed, isCorrectTool)
    }

    private fun dropItems(block: Block, customBlock: CustomBlock) {
        fun dropItem(itemStack: ItemStack) {
            block.world.dropItemNaturally(block.location.offset(0.5, 0.5, 0.5).toLocation(block.world), itemStack)
        }

        if(customBlock.blockProperties().drops() != null) {
            customBlock.blockProperties().drops()?.dataList()?.forEach { data ->
                val random = Math.random()

                if(random > data.chance()) return@forEach

                if(data.item() != null) {
                    dropItem(data.item()!!.createItemStack())
                }

                if(data.experience() > 0.0f) {
                    block.world.spawn(block.location, ExperienceOrb::class.java) { orb ->
                        orb.experience = data.experience()
                    }
                }
            }
        } else {
            dropItem(customBlock.itemStack())
        }
    }
}