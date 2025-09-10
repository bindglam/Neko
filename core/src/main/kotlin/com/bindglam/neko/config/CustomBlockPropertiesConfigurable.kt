package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.block.CustomBlockProperties
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

class CustomBlockPropertiesConfigurable : Configurable<CustomBlockProperties, ConfigurationSection> {
    companion object {
        private val CORRECT_TOOLS_CONFIGURABLE = CorrectToolsConfigurable()
        private val DROPS_CONFIGURABLE = DropsConfigurable()
        private val DROP_DATA_CONFIGURABLE = DropsConfigurable.DropDataConfigurable()
    }

    override fun load(config: ConfigurationSection?): CustomBlockProperties? = config?.let { CustomBlockProperties(
        KEY_CONFIGURABLE.load(config.getString("block-model"))!!,
        BuiltInRegistries.MECHANISMS.get(KEY_CONFIGURABLE.load(config.getString("mechanism"))),
        config.getDouble("hardness").toFloat(),
        CORRECT_TOOLS_CONFIGURABLE.load(config.getConfigurationSection("correct-tools")),
        DROPS_CONFIGURABLE.load(config.getConfigurationSection("drops"))
    ) }


    private class CorrectToolsConfigurable : Configurable<CustomBlockProperties.CorrectTools, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CustomBlockProperties.CorrectTools? = config?.let { CustomBlockProperties.CorrectTools(
            Configurable.parseAsList(config.getStringList("tags"), TagConfigurable("items", Material::class.java)),
            Configurable.parseAsList(config.getStringList("items"), ITEM_TYPE_CONFIGURABLE)
        ) }
    }

    private class DropsConfigurable : Configurable<CustomBlockProperties.Drops, ConfigurationSection> {
        override fun load(config: ConfigurationSection?): CustomBlockProperties.Drops? = config?.let {
            CustomBlockProperties.Drops(config.getKeys(false).map { key -> DROP_DATA_CONFIGURABLE.load(config.getConfigurationSection(key)) })
        }

        class DropDataConfigurable : Configurable<CustomBlockProperties.Drops.DropData, ConfigurationSection> {
            override fun load(config: ConfigurationSection?): CustomBlockProperties.Drops.DropData? = config?.let {
                CustomBlockProperties.Drops.DropData(ITEM_TYPE_CONFIGURABLE.load(config.getString("item")), config.getInt("experience"), config.getDouble("chance", 1.0).toFloat())
            }
        }
    }
}