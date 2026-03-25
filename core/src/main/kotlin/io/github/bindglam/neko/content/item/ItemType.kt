package io.github.bindglam.neko.content.item

import io.github.bindglam.neko.content.ContentType
import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.content.item.properties.ItemProperties
import io.github.bindglam.neko.manager.RegistryManager
import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.utils.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

object ItemType : ContentType<Item> {
    val KEY = Key.key(PLUGIN_ID, "item")

    override fun id() = "item"
    override fun clazz() = Item::class.java

    override fun load(registries: Registries, config: ConfigurationSection): ContentType.LoadResult {
        try {
            val key = Key.key(config.name)
            val properties = loadProperties(config.getConfigurationSection("properties")
                ?: return ContentType.LoadResult.failure("Missing properties section"))
            val features = config.getConfigurationSection("features")?.let { it.getKeys(false).map { key -> it.getConfigurationSection(key)!! } }?.map { featureConfig ->
                return@map RegistryManager.GlobalRegistries.registries().features()[Key.key(featureConfig.getString("id") ?: error("No feature id in ${key.asString()}"))]
                    .orElseThrow { IllegalStateException("Unknown feature in ${key.asString()}") }
            } ?: listOf()

            registries.item().register(key) { entry -> entry
                .key(key)
                .properties(properties)
                .features(features)
            }

            return ContentType.LoadResult.success()
        } catch (e: Exception) {
            return ContentType.LoadResult.failure(e.message ?: "Unknown error")
        }
    }

    private fun loadProperties(config: ConfigurationSection): ItemProperties {
        return ItemProperties.builder()
            .type(Material.valueOf(config.getString("type") ?: "PAPER"))
            .name(config.getString("name")?.let { MiniMessage.miniMessage().deserialize(it) })
            .lore(config.getStringList("lore").map { MiniMessage.miniMessage().deserialize(it) })
            .build()
    }
}