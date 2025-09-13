package com.bindglam.neko.config.content

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.CustomItemProperties
import com.bindglam.neko.utils.ITEM_TYPE_CONFIGURABLE
import com.bindglam.neko.utils.KEY_CONFIGURABLE
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemType

class CustomItemPropertiesConfigurable : Configurable<CustomItemProperties, ConfigurationSection> {
    override fun load(config: ConfigurationSection?): CustomItemProperties? = config?.let { CustomItemProperties(
        ITEM_TYPE_CONFIGURABLE.load(config.getString("type"))?.itemStack()?.type?.asItemType() ?: ItemType.PAPER,
        config.getRichMessage("name"),
        config.getStringList("lore").map { MiniMessage.miniMessage().deserialize(it) },
        KEY_CONFIGURABLE.load(config.getString("model"))
    ) }
}