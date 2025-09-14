package com.bindglam.neko

import com.bindglam.neko.api.Neko
import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.manager.CacheManager
import com.bindglam.neko.api.manager.ContentManager
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.manager.PlayerNetworkManager
import com.bindglam.neko.api.manager.Reloadable
import com.bindglam.neko.api.nms.NMSHook
import com.bindglam.neko.listeners.InventoryListener
import com.bindglam.neko.listeners.ItemListener
import com.bindglam.neko.listeners.PlayerListener
import com.bindglam.neko.manager.CacheManagerImpl
import com.bindglam.neko.manager.CommandManagerImpl
import com.bindglam.neko.manager.ContentManagerImpl
import com.bindglam.neko.manager.PackManagerImpl
import com.bindglam.neko.manager.PlayerNetworkManagerImpl
import com.bindglam.neko.utils.MCVersion
import de.tr7zw.changeme.nbtapi.NBT
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class NekoPlugin : Neko, JavaPlugin() {
    private val managers by lazy {
        listOf(
            CommandManagerImpl,
            CacheManagerImpl,
            ContentManagerImpl,
            PackManagerImpl,
            PlayerNetworkManagerImpl
        )
    }

    private lateinit var nmsHook: NMSHook

    override fun onEnable() {
        if(!NBT.preloadApi()) {
            logger.severe("Failed to load NBT-API! Disable the plugin...")
            server.pluginManager.disablePlugin(this)
            return
        }

        NekoProvider.register(this)

        saveDefaultConfig()

        server.pluginManager.registerEvents(ItemListener(), this)
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)

        val version = MCVersion.parse(Bukkit.getBukkitVersion().substringBefore('-'))

        logger.info("Minecraft Version : $version")

        nmsHook = when(version) {
            MCVersion.v1_21_R5 -> com.bindglam.neko.nms.v1_21_R5.NMSHookImpl
            else -> TODO()
        }

        try {
            managers.forEach { it.start() }
        } catch (e: Exception) {
            slF4JLogger.error("Failed to load", e)
        }
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
    override fun playerNetworkManager(): PlayerNetworkManager = PlayerNetworkManagerImpl
    override fun nms(): NMSHook = nmsHook
}