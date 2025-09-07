package com.bindglam.neko.config

import com.bindglam.neko.api.config.Configurable
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemType

class ItemTypeConfigurable : Configurable<ItemType, String> {
    override fun load(value: String?): ItemType? = value?.let { RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM)[Key.key(value)] }
}