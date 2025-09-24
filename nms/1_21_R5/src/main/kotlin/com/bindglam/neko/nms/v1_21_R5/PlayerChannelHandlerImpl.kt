package com.bindglam.neko.nms.v1_21_R5

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.nms.PlayerChannelHandler
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ServerGamePacketListener
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
import org.bukkit.GameMode
import org.bukkit.craftbukkit.util.CraftLocation
import org.bukkit.entity.Player

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

    private fun <T : ClientGamePacketListener> Packet<in T>.handleClientbound(): Packet<in T>? {
        return this
    }

    private fun <T : ServerGamePacketListener> Packet<in T>.handleServerbound(): Packet<in T>? {
        when (this) {
            is ServerboundPlayerActionPacket -> {
                if(action == ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK || action == ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK) {
                    if (player.gameMode != GameMode.SURVIVAL) return this

                    NekoProvider.neko().contentManager().customBlock(CraftLocation.toBukkit(pos, player.world).block) ?: return this

                    return null
                }
            }
        }
        return this
    }

    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        super.write(ctx, if (msg is Packet<*>) msg.handleClientbound() ?: return else msg, promise)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        super.channelRead(ctx, if (msg is Packet<*>) msg.handleServerbound() ?: return else msg)
    }
}