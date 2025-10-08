package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.block.properties.BlockProperties
import com.bindglam.neko.api.content.block.properties.CorrectTools
import com.bindglam.neko.api.content.block.properties.Drops
import com.bindglam.neko.api.content.block.properties.Sounds
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.ItemTypeConfigurable
import com.bindglam.neko.config.KeyConfigurable
import com.bindglam.neko.config.TagConfigurable
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

object CustomBlockPropertiesConfigurable : Configurable<BlockProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): BlockProperties? = config?.let { BlockProperties.builder()
        .model(KeyConfigurable.load(config.getString("model"))!!)
        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(KeyConfigurable.load(config.getString("renderer"))!!))
        .hardness(config.getDouble("hardness").toFloat())
        .correctTools(CorrectToolsConfigurable.load(config.getConfigurationSection("correct-tools")))
        .dropSilkTouch(config.getBoolean("drop-silk-touch"))
        .drops(DropsConfigurable.load(config.getConfigurationSection("drops")))
        .sounds(SoundsConfigurable.load(config.getConfigurationSection("sounds")))
        .build()
    }


    private object CorrectToolsConfigurable : Configurable<CorrectTools, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CorrectTools? = config?.let { CorrectTools.builder()
            .tags(Configurable.parseAsList(config.getStringList("tags"), TagConfigurable("items", Material::class.java)))
            .whitelist(Configurable.parseAsList(config.getStringList("whitelist"), ItemTypeConfigurable))
            .blacklist(Configurable.parseAsList(config.getStringList("blacklist"), ItemTypeConfigurable))
        }
    }

    private object DropsConfigurable : Configurable<Drops, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Drops? = config?.let {
            Drops.builder()
                .data(config.getKeys(false).map { key -> DropDataConfigurable.load(config.getConfigurationSection(key)) })
        }

        object DropDataConfigurable : Configurable<Drops.DropData, ConfigurationSection> {
            override fun load(config: ConfigurationSection?): Drops.DropData? = config?.let {
                Drops.DropData(ItemTypeConfigurable.load(config.getString("item")), config.getInt("experience"), config.getDouble("chance", 1.0).toFloat())
            }
        }
    }

    private object SoundsConfigurable : Configurable<Sounds, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Sounds? = config?.let { Sounds(
            KeyConfigurable.load(config.getString("place"))!!,
            KeyConfigurable.load(config.getString("break"))!!
        ) }
    }
}