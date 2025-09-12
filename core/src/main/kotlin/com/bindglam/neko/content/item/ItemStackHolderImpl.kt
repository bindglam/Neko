package com.bindglam.neko.content.item

import com.bindglam.neko.api.content.item.ItemStackHolder
import com.bindglam.neko.api.registry.BuiltInRegistries
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack

class ItemStackHolderImpl(private val key: Key) : ItemStackHolder {
    constructor(itemStack: ItemStack) : this(itemStack.type.key())

    override fun itemStack(): ItemStack = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM)[key]?.createItemStack() ?: BuiltInRegistries.ITEMS.getOrNull(key)!!.itemStack()
}