package io.github.bindglam.neko.manager

import io.github.bindglam.neko.content.feature.FeatureContext
import io.github.bindglam.neko.utils.DATA_FOLDER
import io.github.bindglam.neko.utils.logger
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
        generateResourcePack()
    }

    override fun end(context: Context) {
    }

    override fun generateResourcePack() {
        logger().info("Generating resource pack...")

        val resourcePack = ResourcePack.resourcePack()

        resourcePack.packMeta(PackMeta.of(PackFormat.format(99, 1, Int.MAX_VALUE), Component.text("Created by Neko")))

        RegistryManager.GlobalRegistries.registries().item().forEach { content -> content.features().forEach { it.pack(FeatureContext.Pack(content, resourcePack)) } }

        MinecraftResourcePackWriter.minecraft().writeToZipFile(generatedPackFile, resourcePack)

        logger().info("Successfully generated resource pack!")
    }
}