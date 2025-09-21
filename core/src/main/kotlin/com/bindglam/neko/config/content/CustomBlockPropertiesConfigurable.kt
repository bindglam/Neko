package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.block.CustomBlockProperties
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.config.TagConfigurable
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

class CustomBlockPropertiesConfigurable : Configurable<CustomBlockProperties, ConfigurationSection> {
    companion object {
        private val CORRECT_TOOLS_CONFIGURABLE = CorrectToolsConfigurable()
        private val DROPS_CONFIGURABLE = DropsConfigurable()
        private val SOUNDS_CONFIGURABLE = SoundsConfigurable()
    }

    override fun load(config: ConfigurationSection?): CustomBlockProperties? = config?.let { CustomBlockProperties.builder()
        .model(KEY_CONFIGURABLE.load(config.getString("model"))!!)
        .renderer(BuiltInRegistries.BLOCK_RENDERERS.get(KEY_CONFIGURABLE.load(config.getString("renderer"))))
        .hardness(config.getDouble("hardness").toFloat())
        .correctTools(CORRECT_TOOLS_CONFIGURABLE.load(config.getConfigurationSection("correct-tools")))
        .drops(DROPS_CONFIGURABLE.load(config.getConfigurationSection("drops")))
        .sounds(SOUNDS_CONFIGURABLE.load(config.getConfigurationSection("sounds")))
        .build()
    }


    private class CorrectToolsConfigurable : Configurable<CustomBlockProperties.CorrectTools, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CustomBlockProperties.CorrectTools? = config?.let { CustomBlockProperties.CorrectTools(
            Configurable.parseAsList(config.getStringList("tags"), TagConfigurable("items", Material::class.java)),
            Configurable.parseAsList(config.getStringList("items"), ITEM_TYPE_CONFIGURABLE)
        ) }
    }

    private class DropsConfigurable : Configurable<CustomBlockProperties.Drops, ConfigurationSection> {
        companion object {
            private val DROP_DATA_CONFIGURABLE = DropDataConfigurable()
        }

        override fun load(config: ConfigurationSection?): CustomBlockProperties.Drops? = config?.let {
            CustomBlockProperties.Drops(config.getKeys(false).map { key -> DROP_DATA_CONFIGURABLE.load(config.getConfigurationSection(key)) })
        }

        class DropDataConfigurable : Configurable<CustomBlockProperties.Drops.DropData, ConfigurationSection> {
            override fun load(config: ConfigurationSection?): CustomBlockProperties.Drops.DropData? = config?.let {
                CustomBlockProperties.Drops.DropData(ITEM_TYPE_CONFIGURABLE.load(config.getString("item")), config.getInt("experience"), config.getDouble("chance", 1.0).toFloat())
            }
        }
    }

    private class SoundsConfigurable : Configurable<CustomBlockProperties.Sounds, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CustomBlockProperties.Sounds? = config?.let { CustomBlockProperties.Sounds(
            KEY_CONFIGURABLE.load(config.getString("place"))!!,
            KEY_CONFIGURABLE.load(config.getString("break"))!!
        ) }
    }
}