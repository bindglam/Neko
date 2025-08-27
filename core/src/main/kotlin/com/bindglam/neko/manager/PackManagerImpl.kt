package com.bindglam.neko.manager

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.item.CustomItem
import com.bindglam.neko.api.manager.PackManager
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.pack.ItemData
import com.bindglam.neko.pack.PackFile
import com.bindglam.neko.pack.PackZipper
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import org.slf4j.LoggerFactory
import java.io.File
import java.util.stream.Collectors

object PackManagerImpl : PackManager {
    private val LOGGER = LoggerFactory.getLogger(PackManager::class.java)

    private val RESOURCEPACK_FOLDER = File("plugins/Neko/resourcepack")
    private val BUILD_ZIP = File("plugins/Neko/build.zip")

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
    }

    override fun end() {
    }

    override fun pack() {
        BUILD_ZIP.deleteOnExit()

        val startMillis = System.currentTimeMillis()

        val zipper = PackZipper(BUILD_ZIP)

        BuiltInRegistries.ITEMS.entrySet().forEach { entry ->
            createItemFile(entry.value, zipper)
        }

        mergeResourcePacks(zipper)

        zipper.build {
            LOGGER.info("Successfully generated resourcepack (${System.currentTimeMillis() - startMillis}ms)")
        }
    }

    private fun mergeResourcePacks(zipper: PackZipper) {
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
                zipper.addDirectory(pack)
            }
        }
    }

    private fun createItemFile(item: CustomItem, zipper: PackZipper) {
        val modelPath = item.properties().itemModel ?: return

        val bytes = GSON.toJson(ItemData(ItemData.Model(ItemData.Type.MODEL, modelPath.asString()))).toByteArray()

        val filePath = Key.key(item.key().namespace(), "items/${item.key().value()}").toPackPath("json")
        zipper.addFile(filePath, PackFile({ bytes }, bytes.size.toLong()))
    }

    override fun getFile(path: String): File = File(RESOURCEPACK_FOLDER, path)
}