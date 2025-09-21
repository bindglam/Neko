package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.ItemStackHolder
import com.bindglam.neko.api.content.item.ItemStackWrapper
import org.bukkit.NamespacedKey

class ItemTypeConfigurable : Configurable<ItemStackHolder, String> {
    override fun load(value: String?): ItemStackHolder? = value?.let { ItemStackWrapper.of(NamespacedKey.fromString(value)) }
}