package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.Item
import com.bindglam.neko.api.content.item.ItemWrapper
import org.bukkit.NamespacedKey

class ItemTypeConfigurable : Configurable<Item, String> {
    override fun load(value: String?): Item? = value?.let { ItemWrapper.of(NamespacedKey.fromString(value)) }
}