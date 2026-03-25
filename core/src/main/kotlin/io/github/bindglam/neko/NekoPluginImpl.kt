package io.github.bindglam.neko

import io.github.bindglam.neko.manager.CommandManager
import io.github.bindglam.neko.manager.ContentManager
import io.github.bindglam.neko.manager.ContentManagerImpl
import io.github.bindglam.neko.manager.Context
import io.github.bindglam.neko.manager.RegistryManagerImpl
import io.github.bindglam.neko.manager.ResourcePackManager
import io.github.bindglam.neko.manager.ResourcePackManagerImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.plugin.java.JavaPlugin

class NekoPluginImpl : JavaPlugin(), NekoPlugin {
    private val managers = listOf(
        ContentManagerImpl,
        RegistryManagerImpl,
        ResourcePackManagerImpl,
        CommandManager
    )

    override fun onEnable() {
        Neko.registerPlugin(this)

        managers.forEach { it.preload(Context(this)) }

        server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun ServerLoadEvent.onStartup() {
                if(type == ServerLoadEvent.LoadType.RELOAD) return

                managers.forEach { it.start(Context(this@NekoPluginImpl)) }
            }
        }, this)
    }

    override fun onDisable() {
        managers.forEach { it.end(Context(this)) }

        Neko.unregisterPlugin()
    }

    override fun registryManager() = RegistryManagerImpl
    override fun contentManager() = ContentManagerImpl
    override fun resourcePackManager() = ResourcePackManagerImpl
}