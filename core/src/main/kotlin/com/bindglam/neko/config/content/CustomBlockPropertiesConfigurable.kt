package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.block.properties.BlockProperties
import com.bindglam.neko.api.content.block.properties.CorrectTools
import com.bindglam.neko.api.content.block.properties.Drops
import com.bindglam.neko.api.content.block.properties.Sounds
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.TagConfigurable
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

class CustomBlockPropertiesConfigurable : Configurable<BlockProperties, ConfigurationSection> {
    companion object {
        private val CORRECT_TOOLS_CONFIGURABLE = CorrectToolsConfigurable()
        private val DROPS_CONFIGURABLE = DropsConfigurable()
        private val SOUNDS_CONFIGURABLE = SoundsConfigurable()
    }

    override fun load(config: ConfigurationSection?): BlockProperties? = config?.let { BlockProperties.builder()
        .model(KEY_CONFIGURABLE.load(config.getString("model"))!!)
        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(KEY_CONFIGURABLE.load(config.getString("renderer"))))
        .hardness(config.getDouble("hardness").toFloat())
        .correctTools(CORRECT_TOOLS_CONFIGURABLE.load(config.getConfigurationSection("correct-tools")))
        .dropSilkTouch(config.getBoolean("drop-silk-touch"))
        .drops(DROPS_CONFIGURABLE.load(config.getConfigurationSection("drops")))
        .sounds(SOUNDS_CONFIGURABLE.load(config.getConfigurationSection("sounds")))
        .build()
    }


    private class CorrectToolsConfigurable : Configurable<CorrectTools, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CorrectTools? = config?.let { CorrectTools.builder()
            .tags(Configurable.parseAsList(config.getStringList("tags"), TagConfigurable("items", Material::class.java)))
            .whitelist(Configurable.parseAsList(config.getStringList("whitelist"), ITEM_TYPE_CONFIGURABLE))
            .blacklist(Configurable.parseAsList(config.getStringList("blacklist"), ITEM_TYPE_CONFIGURABLE))
        }
    }

    private class DropsConfigurable : Configurable<Drops, ConfigurationSection> {
        companion object {
            private val DROP_DATA_CONFIGURABLE = DropDataConfigurable()
        }

        override fun load(config: ConfigurationSection?): Drops? = config?.let {
            Drops.builder()
                .data(config.getKeys(false).map { key -> DROP_DATA_CONFIGURABLE.load(config.getConfigurationSection(key)) })
        }

        class DropDataConfigurable : Configurable<Drops.DropData, ConfigurationSection> {
            override fun load(config: ConfigurationSection?): Drops.DropData? = config?.let {
                Drops.DropData(ITEM_TYPE_CONFIGURABLE.load(config.getString("item")), config.getInt("experience"), config.getDouble("chance", 1.0).toFloat())
            }
        }
    }

    private class SoundsConfigurable : Configurable<Sounds, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): Sounds? = config?.let { Sounds(
            KEY_CONFIGURABLE.load(config.getString("place"))!!,
            KEY_CONFIGURABLE.load(config.getString("break"))!!
        ) }
    }
}