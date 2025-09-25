package com.bindglam.neko.nms.v1_21_R3

import com.bindglam.neko.api.nms.NMSHook
import com.bindglam.neko.api.nms.PlayerChannelHandler
import org.bukkit.entity.Player

object NMSHookImpl : NMSHook {
    override fun createChannelHandler(player: Player): PlayerChannelHandler = PlayerChannelHandlerImpl(player)
}