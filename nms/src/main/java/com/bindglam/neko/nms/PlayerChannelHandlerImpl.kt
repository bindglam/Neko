package com.bindglam.neko.nms

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.nms.PlayerChannelHandler
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
import net.minecraft.network.protocol.game.ServerGamePacketListener
import net.minecraft.network.protocol.game.ServerboundInteractPacket
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket
import org.bukkit.Bukkit
import org.bukkit.Tag
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.block.TileState
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

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

    private fun <T : ServerGamePacketListener> Packet<in T>.handle(): Packet<in T>? {
        when (this) {
            is ServerboundUseItemOnPacket -> {
            }
        }
        return this
    }

    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        super.write(ctx, msg, promise)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        super.channelRead(ctx, if (msg is Packet<*>) msg.handle() ?: return else msg)
    }
}