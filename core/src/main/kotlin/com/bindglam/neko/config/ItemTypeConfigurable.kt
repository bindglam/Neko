package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import com.bindglam.neko.api.content.item.ItemStackHolder
import com.bindglam.neko.api.registry.BuiltInRegistries
import com.bindglam.neko.content.item.ItemStackHolderImpl
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key

class ItemTypeConfigurable : Configurable<ItemStackHolder, String> {
    override fun load(value: String?): ItemStackHolder? = value?.let { ItemStackHolderImpl(Key.key(value)) }
}