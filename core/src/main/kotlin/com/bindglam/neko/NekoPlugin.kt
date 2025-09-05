package com.bindglam.neko

import com.bindglam.neko.api.Neko
import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.manager.CacheManager
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.manager.Reloadable
import com.bindglam.neko.listeners.ItemListener
import com.bindglam.neko.manager.CacheManagerImpl
import com.bindglam.neko.manager.CommandManagerImpl
import com.bindglam.neko.manager.ContentManagerImpl
import com.bindglam.neko.manager.PackManagerImpl
import org.bukkit.plugin.java.JavaPlugin

class NekoPlugin : Neko, JavaPlugin() {
    private val managers by lazy {
        listOf(
            CommandManagerImpl,
            CacheManagerImpl,
            ContentManagerImpl,
            PackManagerImpl
        )
    }

    override fun onEnable() {
        NekoProvider.register(this)

        saveDefaultConfig()

        server.pluginManager.registerEvents(ItemListener(), this)

        managers.forEach { it.start() }
    }

    override fun onDisable() {
        managers.forEach { it.end() }
    }

    override fun reload(): Neko.ReloadInfo {
        val reloadableList = managers.stream().filter { it is Reloadable }.toList()

        try {
            reloadableList.forEach { it.end() }
            reloadableList.forEach { it.start() }
        } catch (e: Exception) {
            slF4JLogger.error("Failed to reload", e)
            return Neko.ReloadInfo.FAIL
        }

        return Neko.ReloadInfo.SUCCESS
    }

    override fun cacheManager(): CacheManager = CacheManagerImpl
    override fun contentManager(): ContentManager = ContentManagerImpl
    override fun packManager(): PackManager = PackManagerImpl
}