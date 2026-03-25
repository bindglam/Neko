package io.github.bindglam.neko.manager

import io.github.bindglam.neko.utils.DATA_FOLDER
import net.kyori.adventure.text.Component
import team.unnamed.creative.ResourcePack
import team.unnamed.creative.metadata.pack.PackFormat
import team.unnamed.creative.metadata.pack.PackMeta
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter
import java.io.File

object ResourcePackManagerImpl : ResourcePackManager, Managerial {
    private val generatedPackFile = File(DATA_FOLDER, "generated.zip")

    override fun preload(context: Context) {
    }

    override fun start(context: Context) {
    }

    override fun end(context: Context) {
    }

    override fun generateResourcePack() {
        val resourcePack = ResourcePack.resourcePack()

        resourcePack.packMeta(PackMeta.of(PackFormat.format(99, 1, Int.MAX_VALUE), Component.text("Created by Neko")))

        MinecraftResourcePackWriter.minecraft().writeToZipFile(generatedPackFile, resourcePack)
    }
}