package com.bindglam.neko

import com.bindglam.neko.manager.CommandManager
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap

@Suppress("unstableApiUsage")
class NekoBootstrap : PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        CommandManager.init(context)
    }
}