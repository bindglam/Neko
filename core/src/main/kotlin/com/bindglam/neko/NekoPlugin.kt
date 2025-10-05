package com.bindglam.neko

import com.bindglam.neko.api.Neko
import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.manager.*
import com.bindglam.neko.api.nms.NMSHook
import com.bindglam.neko.listeners.CustomBlockListener
import com.bindglam.neko.listeners.CustomFurnitureListener
import com.bindglam.neko.listeners.InventoryListener
import com.bindglam.neko.listeners.CustomItemListener
import com.bindglam.neko.listeners.NekoListener
import com.bindglam.neko.listeners.PlayerListener
import com.bindglam.neko.listeners.ServerListener
import com.bindglam.neko.manager.*
import com.bindglam.neko.utils.MCVersion
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class NekoPlugin : Neko, JavaPlugin() {
    private val managers by lazy {
        listOf(
            CacheManagerImpl,
            ContentManagerImpl,
            PackManagerImpl,
            PlayerNetworkManagerImpl
        )
    }

    private lateinit var nmsHook: NMSHook

    override fun onEnable() {
        NekoProvider.register(this)

        saveDefaultConfig()

        server.pluginManager.registerEvents(CustomItemListener(), this)
        server.pluginManager.registerEvents(CustomBlockListener(), this)
        server.pluginManager.registerEvents(CustomFurnitureListener(), this)
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        server.pluginManager.registerEvents(NekoListener(), this)
        server.pluginManager.registerEvents(ServerListener(), this)

        val version = MCVersion.parse(Bukkit.getBukkitVersion().substringBefore('-')).also {
            logger.info("Minecraft Version : $it")
        }

        nmsHook = when(version) {
            MCVersion.v1_21_R6 -> com.bindglam.neko.nms.v1_21_R6.NMSHookImpl
            MCVersion.v1_21_R3 -> com.bindglam.neko.nms.v1_21_R3.NMSHookImpl
            else -> throw IllegalStateException("Unsupported minecraft version")
        }

        server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun ServerLoadEvent.loadServices() {
                try {
                    StartupProcess().use { it.start(managers) }
                } catch (e: Exception) {
                    slF4JLogger.error("Failed to load", e)
                }
            }
        }, this)

        YamlConfiguration.loadConfiguration(File("config/paper-global.yml")).also { paperConfig ->
            if(!paperConfig.getBoolean("block-updates.disable-noteblock-updates")) {
                logger.warning("Please enable block-updates.disable-noteblock-updates in paper-global.yml, or custom block will not work properly!")
            }
        }
    }

    override fun onDisable() {
        ShutdownProcess().use { it.start(managers) }
    }

    override fun reload(sender: CommandSender): Neko.ReloadInfo {
        val reloadableList = managers.stream().filter { it is Reloadable }.toList()

        try {
            ReloadProcess(sender).use { it.start(reloadableList) }
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