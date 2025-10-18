package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.ItemStackReference
import com.bindglam.neko.api.content.item.ItemStackWrapper
import org.bukkit.NamespacedKey

object ItemTypeConfigurable : Configurable<ItemStackReference, String> {
    override fun load(value: String?): ItemStackReference? = value?.let { ItemStackWrapper.of(NamespacedKey.fromString(value)) }
}