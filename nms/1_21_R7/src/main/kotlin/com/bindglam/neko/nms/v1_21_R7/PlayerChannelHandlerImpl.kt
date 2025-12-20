package com.bindglam.neko.nms.v1_21_R7

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.event.BlockBreakStateEvent
import com.bindglam.neko.api.nms.PlayerChannelHandler
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.minecraft.core.component.DataComponents
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemLore
import org.bukkit.entity.Player
import java.util.function.BiFunction

class PlayerChannelHandlerImpl(private val player: Player) : PlayerChannelHandler, ChannelDuplexHandler() {
    companion object {
        private const val INJECT_NAME = "neko_channel_handler"
    }

    private val connection = player.handle.connection

    init {
        val pipeline = connection.connection.channel.pipeline()
        pipeline.toMap().forEach {
            if (it.value is Connection) pipeline.addBefore(it.key, INJECT_NAME, this)
        }
    }

    override fun close() {
        val channel = connection.connection.channel
        channel.eventLoop().submit {
            channel.pipeline().remove(INJECT_NAME)
        }
    }

    private fun <T : ClientGamePacketListener> Packet<in T>.handleClientbound(): Packet<in T> {
        fun mapClientsideLore(itemStack: ItemStack, function: BiFunction<org.bukkit.inventory.ItemStack, Player, List<Component>>?) {
            function ?: return
            val lore = function.apply(itemStack.bukkitStack, player)
            itemStack.set(DataComponents.LORE, ItemLore(lore.map { PaperAdventure.asVanilla(it) }))
        }

        when (this) {
            is ClientboundContainerSetContentPacket -> {
                val items = arrayListOf<ItemStack>().apply {
                    items.forEach {
                        add(it.copy().apply {
                            val customItem = NekoProvider.neko().contentManager().customItem(bukkitStack) ?: return@apply
                            mapClientsideLore(this, customItem.properties().clientsideLore())
                        })
                    }
                }

                val carriedItem = carriedItem.copy().apply {
                    val customItem = NekoProvider.neko().contentManager().customItem(bukkitStack) ?: return@apply
                    mapClientsideLore(this, customItem.properties().clientsideLore())
                }

                return ClientboundContainerSetContentPacket(containerId, stateId, items, carriedItem)
            }

            is ClientboundContainerSetSlotPacket -> {
                val item = item.copy().apply {
                    val customItem = NekoProvider.neko().contentManager().customItem(bukkitStack) ?: return@apply
                    mapClientsideLore(this, customItem.properties().clientsideLore())
                }

                return ClientboundContainerSetSlotPacket(containerId, stateId, slot, item)
            }

            is ClientboundSetPlayerInventoryPacket -> {
                val contents = contents.copy().apply {
                    val customItem = NekoProvider.neko().contentManager().customItem(bukkitStack) ?: return@apply
                    mapClientsideLore(this, customItem.properties().clientsideLore())
                }

                return ClientboundSetPlayerInventoryPacket(slot, contents)
            }
        }
        return this
    }

    private fun <T : ServerGamePacketListener> Packet<in T>.handleServerbound(): Packet<in T> {
        when (this) {
            is ServerboundPlayerActionPacket -> {
                val blockLoc = pos.toLocation(player.world)

                when(action) {
                    ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK -> {
                        NekoProvider.neko().scheduler().run(blockLoc) {
                            BlockBreakStateEvent(blockLoc.block, player, BlockBreakStateEvent.State.START).callEvent()
                        }
                    }

                    ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK -> {
                        NekoProvider.neko().scheduler().run(blockLoc) {
                            BlockBreakStateEvent(blockLoc.block, player, BlockBreakStateEvent.State.END).callEvent()
                        }
                    }

                    else -> {}
                }
            }
        }
        return this
    }

    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        super.write(ctx, if (msg is Packet<*>) msg.handleClientbound() else msg, promise)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        super.channelRead(ctx, if (msg is Packet<*>) msg.handleServerbound() else msg)
    }
}