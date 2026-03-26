package io.github.bindglam.neko.manager

import io.github.bindglam.neko.Neko
import io.github.bindglam.neko.content.ContentsPackImpl
import io.github.bindglam.neko.content.PackLoader
import io.github.bindglam.neko.content.feature.builtin.HelloWorldFeature
import io.github.bindglam.neko.event.RegistryInitializeEvent
import io.github.bindglam.neko.manager.RegistryManagerImpl.GlobalRegistriesImpl
import io.github.bindglam.neko.utils.DATA_FOLDER
import io.github.bindglam.neko.utils.logger
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*

object ContentManagerImpl : ContentManager, Managerial, Reloadable, Listener {
    private val packsFolder = File(DATA_FOLDER, "packs")

    override fun preload(context: Context) {
        Bukkit.getPluginManager().registerEvents(this, context.plugin())
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun RegistryInitializeEvent.loadPacks() {
        GlobalRegistriesImpl.features().register(HelloWorldFeature.KEY, HelloWorldFeature.Factory)

        loadPacks(registries)
    }

    private fun loadPacks(registries: RegistryManager.GlobalRegistries) {
        logger().info("Loading packs...")

        if(!packsFolder.exists()) {
            packsFolder.mkdirs()
        }

        var loadedPacksCnt = 0
        packsFolder.listFiles().forEach { packFolder ->
            val registrar = PackLoader.loadPack(packFolder)
            if(registrar.isFailure) {
                logger().warning("Failed to load ${packFolder.name}. ( ${registrar.errorMsg} )")
                return@forEach
            }

            val pack = registries.contentsPacks().register(ContentsPackImpl.createKey(registrar.id!!), registrar.pack!!)
            registries.mergeAll(pack.registries())

            loadedPacksCnt++
        }
        logger().info("Loaded $loadedPacksCnt packs!")

        logger().info("Successfully loaded packs!")
    }

    override fun start(context: Context) {
    }

    override fun end(context: Context) {
        HandlerList.unregisterAll(this)
    }

    override fun getNekoItemByStack(itemStack: ItemStack) =
        Optional.ofNullable(Neko.plugin().registryManager().registries().item().entries().map { it.value() }.find { it.isSimilar(itemStack) })
}