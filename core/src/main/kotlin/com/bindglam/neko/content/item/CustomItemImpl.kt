package com.bindglam.neko.content.item

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.content.item.CustomItemProperties
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.pack.item.ItemData
import com.bindglam.neko.utils.NamespacedKeyDataType
import com.bindglam.neko.utils.plugin
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

open class CustomItemImpl(private val key: NamespacedKey, private val properties: CustomItemProperties) : CustomItem {
    companion object {
        val CUSTOM_ITEM_KEY = NamespacedKey(NekoProvider.neko().plugin(), "custom-item")

        private val GSON = Gson()
    }

    override fun pack(zipper: PackZipper) {
        val modelPath = properties.itemModel ?: return

        GSON.toJson(ItemData(ItemData.Model(ItemData.Type.MODEL, modelPath.asString()))).toByteArray().also {
            val filePath = Key.key(key.namespace(), "items/${key.value()}").toPackPath("json")

            zipper.addFile(filePath, PackFile({ it }, it.size.toLong()))
        }
    }

    override fun itemProperties(): CustomItemProperties = properties

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