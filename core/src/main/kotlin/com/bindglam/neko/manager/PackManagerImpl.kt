package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.event.AsyncGenerateResourcePackEvent
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.pack.host.PackHost
import com.bindglam.neko.pack.PackZipperImpl
import com.bindglam.neko.pack.PackerApplier
import com.bindglam.neko.pack.host.selfhost.SelfHost
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.plugin
import net.kyori.adventure.resource.ResourcePackInfo
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory
import java.io.File
import java.math.BigInteger
import java.net.URI
import java.security.MessageDigest
import java.util.*
import java.util.stream.Collectors

object PackManagerImpl : PackManager {
    private val LOGGER = LoggerFactory.getLogger(PackManager::class.java)

    private val RESOURCEPACK_FOLDER = File("plugins/Neko/resourcepack")
    private val HASH_DIGEST = MessageDigest.getInstance("MD5")

    private val PACK_HOSTS = listOf(SelfHost())

    private lateinit var packInfo: ResourcePackInfo.Builder

    private var packHost: PackHost? = null

    override fun start(process: Process) {
        if(!RESOURCEPACK_FOLDER.exists()) {
            RESOURCEPACK_FOLDER.mkdirs()

            File(RESOURCEPACK_FOLDER, "pack.mcmeta").apply { createIfNotExists() }.bufferedWriter().use { writer ->
                NekoProvider.neko().plugin().getResource("pack.mcmeta")!!.bufferedReader().use { preset ->
                    writer.write(preset.lines().collect(Collectors.joining(System.lineSeparator())))
                }
            }
        }

        pack(process)

        buildPackInfo()

        NekoProvider.neko().plugin().config.also { config ->
            packHost = PACK_HOSTS.find { config.getBoolean("pack.host.${it.id()}.enabled") }

            config.getConfigurationSection("pack.host.${packHost?.id()}")?.let { packHost?.start(it) }

            Bukkit.getOnlinePlayers().forEach { packHost?.sendPack(it, NekoProvider.neko().plugin().config.getRichMessage("pack.prompt-message")!!) }
        }
    }

    override fun end(process: Process) {
        packHost?.end()
    }

    private fun pack(process: Process) {
        PackManager.BUILD_ZIP.deleteOnExit()

        val startMillis = System.currentTimeMillis()

        val zipper = PackZipperImpl(PackManager.BUILD_ZIP)

        mergeResourcePacks(zipper)

        AsyncGenerateResourcePackEvent(zipper).callEvent()

        PackerApplier.apply(zipper)

        zipper.build(process)
        zipper.close()

        LOGGER.info("Successfully generated resourcepack (${System.currentTimeMillis() - startMillis}ms)")
    }

    private fun mergeResourcePacks(zipper: PackZipperImpl) {
        val mergeAfterPacking = NekoProvider.neko().plugin().config.getBoolean("pack.merge-resource-packs.merge-after-packing")
        val mergePacks = NekoProvider.neko().plugin().config.getStringList("pack.merge-resource-packs.packs")

        if(!mergeAfterPacking) {
            mergePacks.map { File("plugins/${it}") }.forEach { pack ->
                zipper.addDirectory(pack)
            }
        }

        zipper.addDirectory(RESOURCEPACK_FOLDER)

        if(mergeAfterPacking) {
            mergePacks.map { File("plugins/${it}") }.forEach { pack ->
                if(pack.exists())
                    zipper.addDirectory(pack)
            }
        }
    }

    private fun buildPackInfo() {
        val packHash = BigInteger(1, HASH_DIGEST.digest(PackManager.BUILD_ZIP.inputStream().readBytes())).toString(16)
        var packUUID = UUID.randomUUID()

        val cache = NekoProvider.neko().cacheManager().getCache("pack")
        if(cache["id"] != null)
            packUUID = UUID.fromString(cache.getString("id"))
        else
            cache["id"] = packUUID.toString()

        packInfo = ResourcePackInfo.resourcePackInfo().id(packUUID).hash(packHash)
    }

    override fun packInfo(uri: URI): ResourcePackInfo = packInfo.uri(uri).build()
    override fun packHost(): PackHost? = packHost
}