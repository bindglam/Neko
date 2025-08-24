package com.bindglam.neko.item

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.item.CustomItem
import com.bindglam.neko.api.item.CustomItemProperties
import com.bindglam.neko.utils.NamespacedKeyDataType
import com.bindglam.neko.utils.plugin
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

class CustomItemImpl(private val key: NamespacedKey, private val properties: CustomItemProperties) : CustomItem {
    companion object {
        private val CUSTOM_ITEM_KEY = NamespacedKey(NekoProvider.neko().plugin(), "custom-item")
    }

    override fun properties(): CustomItemProperties = properties

    override fun key(): Key = key

    override fun itemStack(): ItemStack = properties.itemType.createItemStack().apply {
        itemMeta = itemMeta.apply {
            displayName(properties.displayName)
            lore(properties.lore)
            itemModel = if(properties.itemModel != null) key else null

            persistentDataContainer.set(CUSTOM_ITEM_KEY, NamespacedKeyDataType, key)
        }
    }
}