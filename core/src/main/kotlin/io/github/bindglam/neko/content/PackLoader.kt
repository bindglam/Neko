package io.github.bindglam.neko.content

import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.manager.RegistryManager
import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.registry.RegistriesImpl
import io.github.bindglam.neko.utils.PLUGIN_ID
import io.github.bindglam.neko.utils.listFilesRecursively
import io.github.bindglam.neko.utils.logger
import net.kyori.adventure.key.Key
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object PackLoader {
    fun loadPack(folder: File): LoadResult {
        val settingsFile = File(folder, "pack.yml")
        if(!settingsFile.exists())
            return LoadResult.failure("Missing pack.yml")
        val settingsConfig = YamlConfiguration.loadConfiguration(settingsFile)

        val id = settingsConfig.getString("id") ?: return LoadResult.failure("Missing id in pack.yml")
        val version = settingsConfig.getString("version") ?: return LoadResult.failure("Missing version in pack.yml")
        val author = settingsConfig.getString("author") ?: return LoadResult.failure("Missing author in pack.yml")
        val registries = RegistriesImpl()

        val configsFolder = File(folder, "configs")
        if(configsFolder.exists())
            loadConfigs(configsFolder, registries)

        return LoadResult.success(id) { entry ->
            entry.id(id)
                .version(version)
                .author(author)
                .packFolder(folder)
                .registries(registries)
        }
    }

    private fun loadConfigs(folder: File, registries: Registries) {
        folder.listFilesRecursively().map { YamlConfiguration.loadConfiguration(it) }.forEach { config ->
            config.getKeys(false).map { config.getConfigurationSection(it)!! }.forEach { contentConfig ->
                val type = contentConfig.getString("type")?.let { RegistryManager.GlobalRegistries.registries().types()[Key.key(PLUGIN_ID, it)] }
                    ?.orElse(null)
                if(type == null) {
                    logger().warning("Failed to load ${contentConfig.name}. ( Unknown type )")
                    return@forEach
                }

                val features = arrayListOf<Feature>()
                contentConfig.getConfigurationSection("features")?.let { it.getKeys(false).map { key -> it.getConfigurationSection(key)!! }.forEach { featureConfig ->
                    val featureKey = featureConfig.getString("id")?.let { str -> Key.key(str) }
                    if(featureKey == null) {
                        logger().warning("Unknown feature ( null )")
                        return@forEach
                    }
                    val feature = RegistryManager.GlobalRegistries.registries().features()[featureKey]
                        .orElse(null)
                    if(feature == null) {
                        logger().warning("Unknown feature ( in ${featureKey.asString()} )")
                        return@forEach
                    }

                    features.add(feature)
                } }

                val result = type.load(registries, contentConfig, features)
                if(result.isFailure) {
                    logger().warning("Failed to load ${contentConfig.name}. ( ${result.errorMessage()} )")
                    return@forEach
                }
            }
        }
    }

    class LoadResult private constructor(val id: String?, val registrar: ((ContentsPackRegistryEntry) -> Unit)?, val errorMsg: String?) {
        companion object {
            fun success(id: String, registrar: (ContentsPackRegistryEntry) -> Unit) = LoadResult(id, registrar, null)
            fun failure(errorMsg: String) = LoadResult(null, null, errorMsg)
        }

        val isSuccess: Boolean
            get() = id != null && registrar != null
        val isFailure: Boolean
            get() = errorMsg != null
    }
}