package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.event.AsyncGenerateResourcePackEvent
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.host.PackHost
import com.bindglam.neko.api.pack.minecraft.Float2
import com.bindglam.neko.api.pack.minecraft.PackMeta
import com.bindglam.neko.api.pack.minecraft.font.FontData
import com.bindglam.neko.api.utils.GsonUtils
import com.bindglam.neko.pack.PackZipperImpl
import com.bindglam.neko.pack.PackerApplier
import com.bindglam.neko.pack.host.selfhost.SelfHost
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.plugin
import net.kyori.adventure.resource.ResourcePackInfo
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
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
    private val RESOURCEPACK_META_FILE = File(RESOURCEPACK_FOLDER, "pack.mcmeta")
    private val HASH_DIGEST = MessageDigest.getInstance("MD5")
    private val PACK_HOSTS = listOf(SelfHost())

    private lateinit var packInfo: ResourcePackInfo.Builder

    private var packHost: PackHost? = null

    private lateinit var config: FileConfiguration
    private lateinit var promptMessage: Component
    private lateinit var mergePacks: List<String>
    private var minecraftFont: FontData.TTF? = null

    override fun start(process: Process) {
        config = NekoProvider.neko().plugin().config
        promptMessage = config.getRichMessage("pack.prompt-message")!!
        mergePacks = config.getStringList("pack.merge-resource-packs.packs")
        if(File("plugins/Neko", config.getString("pack.font.file")!!).exists())
            minecraftFont = FontData.TTF(config.getString("pack.font.file")!!, config.getFloatList("pack.font.shift").let { Float2(it[0], it[1]) },
                config.getDouble("pack.font.size").toFloat(), config.getDouble("pack.font.oversample").toFloat(), config.getString("pack.font.skip") ?: "")

        if(!RESOURCEPACK_FOLDER.exists()) {
            RESOURCEPACK_FOLDER.mkdirs()

            RESOURCEPACK_META_FILE.apply { createIfNotExists() }.bufferedWriter().use { writer ->
                NekoProvider.neko().plugin().getResource("pack.mcmeta")!!.bufferedReader().use { preset ->
                    writer.write(preset.lines().collect(Collectors.joining(System.lineSeparator())))
                }
            }
        }

        pack(process)

        buildPackInfo()

        packHost = PACK_HOSTS.find { config.getBoolean("pack.host.${it.id()}.enabled") }
        config.getConfigurationSection("pack.host.${packHost?.id()}")?.let { packHost?.start(it) }

        Bukkit.getOnlinePlayers().forEach { sendPack(it) }
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

        if(minecraftFont != null) {
            zipper.addFile("assets/minecraft/font/${minecraftFont!!.file()}", PackFile(File("plugins/Neko", minecraftFont!!.file()).readBytes()))
            zipper.addComponent("assets/minecraft/font/default.json", FontData(listOf(minecraftFont!!)))
        }

        zipper.build(process)
        zipper.close()

        LOGGER.info("Successfully generated resourcepack (${System.currentTimeMillis() - startMillis}ms)")
    }

    private fun mergeResourcePacks(zipper: PackZipperImpl) {
        var currentMeta = GsonUtils.GSON.fromJson(RESOURCEPACK_META_FILE.bufferedReader().readText(), PackMeta::class.java)

        mergePacks.map { File("plugins/${it}") }.forEach { pack ->
            val externalMetaFile = File(pack, "pack.mcmeta")

            if(externalMetaFile.exists()) {
                val externalMeta = GsonUtils.GSON.fromJson(externalMetaFile.bufferedReader().readText(), PackMeta::class.java)

                currentMeta = PackMeta(currentMeta.pack(),
                    PackMeta.Overlays(ArrayList(currentMeta.overlays()?.entries() ?: listOf()).apply { addAll(externalMeta.overlays()?.entries() ?: listOf()) }))
            }

            zipper.addDirectory(pack)
        }

        zipper.addDirectory(RESOURCEPACK_FOLDER)

        zipper.addFile("pack.mcmeta", PackFile(GsonUtils.GSON.toJson(currentMeta).toByteArray()))
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

    override fun sendPack(player: Player) {
        packHost?.sendPack(player, promptMessage)
    }

    override fun packInfo(uri: URI): ResourcePackInfo = packInfo.uri(uri).build()
    override fun packHost(): PackHost? = packHost
}