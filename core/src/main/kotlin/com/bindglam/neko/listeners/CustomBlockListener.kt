package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.EventState
import com.bindglam.neko.api.content.item.block.BlockItem
import com.bindglam.neko.content.block.BlockHelper
import com.bindglam.neko.utils.CURRENT_TICK
import com.bindglam.neko.utils.canPlaceBlock
import com.bindglam.neko.utils.isInteractable
import com.bindglam.neko.utils.isReplaceable
import com.bindglam.neko.utils.placeBlock
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.FluidCollisionMode
import org.bukkit.GameEvent
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.ExperienceOrb
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerAnimationEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.pow

class CustomBlockListener : Listener {
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

            BlockHelper.updateLastPlaceBlock(player)
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun PlayerInteractEvent.tryPlaceCustomBlock() {
        item ?: return
        hand ?: return
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK) return
        if (clickedBlock!!.isInteractable() && !player.isSneaking) return

        val customItem = NekoProvider.neko().contentManager().customItem(item) ?: return
        if (customItem !is BlockItem) return
        val customBlock = customItem.block()

        val location = if(!clickedBlock!!.type.isReplaceable) clickedBlock!!.getRelative(blockFace).location else clickedBlock!!.location

        if(player.canPlaceBlock(location)) {
            isCancelled = true

            val placeSound = if(customBlock.properties().sounds() != null)
                Sound.sound(customBlock.properties().sounds()!!.placeSound(), Sound.Source.BLOCK, 1f, 1f)
            else
                Sound.sound(org.bukkit.Sound.BLOCK_METAL_PLACE, Sound.Source.BLOCK, 1f, 1f)

            player.placeBlock(location, { customBlock.blockState().copy(it).update(true, true) }, clickedBlock!!, hand!!, placeSound)

            BlockHelper.updateLastPlaceBlock(player)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerInteractEvent.interactCustomBlock() {
        clickedBlock ?: return

        if (action != Action.RIGHT_CLICK_BLOCK || player.isSneaking) return
        if(CURRENT_TICK - BlockHelper.lastPlaceBlock(player) < 3) return

        val customBlock = NekoProvider.neko().contentManager().customBlock(clickedBlock) ?: return

        if(customBlock.onInteract(player, clickedBlock) == EventState.CANCEL)
            isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun PlayerAnimationEvent.tryBreakCustomBlock() {
        if (player.gameMode != GameMode.SURVIVAL) return
        if (CURRENT_TICK - BlockHelper.lastPlaceBlock(player) < 3) return

        val result = player.rayTraceBlocks(player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE)?.value ?: 3.0, FluidCollisionMode.NEVER) ?: return
        val block = result.hitBlock ?: return
        val customBlock = NekoProvider.neko().contentManager().customBlock(block) ?: return

        val blockBreakSpeedData = blockBreakSpeed(player, customBlock)

        player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, 5, Int.MAX_VALUE, false, false, false))

        BlockHelper.updateBreakProgress(player, blockBreakSpeedData.speed)
        player.sendBlockDamage(block.location, BlockHelper.breakProgress(player).coerceAtMost(1f))

        if(BlockHelper.breakProgress(player) >= 1f) {
            BlockHelper.removeBreakProgress(player)

            val event = BlockBreakEvent(block, player)

            if(event.callEvent()) {
                player.inventory.itemInMainHand.editMeta {
                    if(it !is Damageable) return@editMeta

                    val unbreakingLevel = it.getEnchantLevel(Enchantment.UNBREAKING)

                    if(Math.random() <= 1.0/(unbreakingLevel+1))
                        it.damage += 1
                }

                Particle.BLOCK.builder()
                    .location(block.location.toCenterLocation())
                    .offset(0.0, 0.2, 0.0)
                    .count(80)
                    .extra(10.0)
                    .receivers(32, true)
                    .data(block.blockData.clone())
                    .spawn()
                block.type = Material.AIR

                val breakSound = if(customBlock.properties().sounds() != null)
                    Sound.sound(customBlock.properties().sounds()!!.breakSound(), Sound.Source.BLOCK, 1f, 1f)
                else
                    Sound.sound(org.bukkit.Sound.BLOCK_METAL_BREAK, Sound.Source.BLOCK, 1f, 1f)
                block.world.playSound(breakSound, Sound.Emitter.self())

                if(blockBreakSpeedData.isCorrectTool) {
                    dropItems(player, block, customBlock)
                }

                block.world.sendGameEvent(player, GameEvent.BLOCK_DESTROY, block.location.toVector())
            }
        }
    }

    data class BlockBreakSpeedData(
        val speed: Float,
        val isCorrectTool: Boolean
    )

    private fun blockBreakSpeed(player: Player, customBlock: com.bindglam.neko.api.content.block.Block): BlockBreakSpeedData {
        var itemBlockBreakSpeed = 1.0f
        var isCorrectTool = customBlock.properties().correctTools()?.isCorrectTool(player.inventory.itemInMainHand) == true

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

        val efficiencyEffect = player.inventory.itemInMainHand.getEnchantmentLevel(Enchantment.EFFICIENCY).toFloat().pow(2f) + 1f
        val attributeEffect = player.inventory.itemInMainHand.itemMeta.getAttributeModifiers(Attribute.BLOCK_BREAK_SPEED)?.sumOf { it.amount }?.toFloat() ?: 0f

        val blockBreakSpeed = (itemBlockBreakSpeed + efficiencyEffect + attributeEffect) / customBlock.properties().hardness() / if(isCorrectTool) 30f else 100f

        return BlockBreakSpeedData(blockBreakSpeed, isCorrectTool)
    }

    private fun dropItems(player: Player, block: Block, customBlock: com.bindglam.neko.api.content.block.Block) {
        fun dropItem(itemStack: ItemStack) {
            block.world.dropItemNaturally(block.location.offset(0.5, 0.5, 0.5).toLocation(block.world), itemStack)
        }

        val useSilkTouch = customBlock.properties().dropSilkTouch() && player.inventory.itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)

        if(customBlock.properties().drops() != null && !useSilkTouch) {
            customBlock.properties().drops()?.data()?.forEach { data ->
                val random = Math.random()

                if(random > data.chance()) return@forEach

                if(data.item() != null) {
                    dropItem(data.item()!!.itemStack().clone())
                }

                if(data.experience() > 0.0f) {
                    block.world.spawn(block.location, ExperienceOrb::class.java) { orb ->
                        orb.experience = data.experience()
                    }
                }
            }
        } else {
            customBlock.item()?.itemStack()?.let { dropItem(it) }
        }
    }
}