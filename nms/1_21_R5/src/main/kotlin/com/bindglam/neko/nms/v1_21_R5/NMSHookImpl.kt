package com.bindglam.neko.nms.v1_21_R5

import com.bindglam.neko.api.content.item.block.CustomBlock
import com.bindglam.neko.api.nms.NMSHook
import com.bindglam.neko.api.nms.PlayerChannelHandler
import org.bukkit.entity.Player

object NMSHookImpl : NMSHook {
    override fun createChannelHandler(player: Player): PlayerChannelHandler = PlayerChannelHandlerImpl(player)

    override fun <T> registerForNMS(value: T): T? = when(value) {
        is CustomBlock -> NMSBlock(value).also { it.register() } as T
        else -> null
    }
}