package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.CustomItemProperties
import com.bindglam.neko.utils.COMPONENT_CONFIGURABLE
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import org.bukkit.configuration.ConfigurationSection

class CustomItemPropertiesConfigurable : Configurable<CustomItemProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): CustomItemProperties? = config?.let { CustomItemProperties(
        ITEM_TYPE_CONFIGURABLE.load(config.getString("type"))!!,
        COMPONENT_CONFIGURABLE.load(config.getString("display-name")),
        Configurable.parseAsList(config.getStringList("lore"), COMPONENT_CONFIGURABLE),
        KEY_CONFIGURABLE.load(config.getString("item-model"))
    ) }
}