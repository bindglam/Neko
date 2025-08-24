package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.item.CustomItem
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.pack.ItemData
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.plugin
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import net.lingala.zip4j.ZipFile
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

object PackManagerImpl : PackManager {
    private val LOGGER = LoggerFactory.getLogger(PackManager::class.java)

    private val RESOURCEPACK_FOLDER = File("plugins/Neko/resourcepack")
    private val GENERATED_FOLDER = File("plugins/Neko/generated")
    private val GENERATED_ZIP = File("plugins/Neko/generated.zip")

    private val GSON = Gson()

    override fun start() {
        if(!RESOURCEPACK_FOLDER.exists()) {
            RESOURCEPACK_FOLDER.mkdirs()

            val writer = File(RESOURCEPACK_FOLDER, "pack.mcmeta").apply { createIfNotExists() }.bufferedWriter()
            val preset = NekoProvider.neko().plugin().getResource("pack.mcmeta")!!.bufferedReader()
            writer.write(preset.lines().collect(Collectors.joining(System.lineSeparator())))
            writer.close()
            preset.close()
        }

        pack()
        LOGGER.info("Successfully generated resourcepack")
    }

    override fun end() {
    }

    override fun pack() {
        // TODO : Multi-threading packaging

        if(GENERATED_FOLDER.exists())
            GENERATED_FOLDER.deleteRecursively()
        GENERATED_ZIP.deleteOnExit()

        FileUtils.copyDirectory(RESOURCEPACK_FOLDER, GENERATED_FOLDER)

        BuiltInRegistries.ITEMS.entrySet().forEach { entry ->
            createItemFile(entry.value)
        }

        ZipFile(GENERATED_ZIP).apply {
            addFolder(File(GENERATED_FOLDER, "assets"))
            addFile(File(GENERATED_FOLDER, "pack.mcmeta"))
        }
    }

    private fun createItemFile(item: CustomItem) {
        val modelPath = item.properties().itemModel ?: return

        val writer = getGeneratedFile(Key.key(item.key().namespace(), "items/${item.key().value()}"), "json").also { it.createIfNotExists() }
            .bufferedWriter()
        writer.write(GSON.toJson(ItemData(ItemData.Model(ItemData.Type.MODEL, modelPath.asString()))))
        writer.close()
    }

    override fun getFile(path: String): File = File(RESOURCEPACK_FOLDER, path)
    override fun getGeneratedFile(path: String): File = File(GENERATED_FOLDER, path)
}