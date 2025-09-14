package com.bindglam.neko.content.item

import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.Packer
import com.bindglam.neko.pack.item.ItemData
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import net.kyori.adventure.key.Key

open class CustomItemPacker : Packer<CustomItem> {
    companion object {
        private val GSON = Gson()
    }

    override fun pack(zipper: PackZipper, customItem: CustomItem) {
        val modelPath = customItem.itemProperties().model() ?: return

        GSON.toJson(ItemData(ItemData.Model(ItemData.Type.MODEL, modelPath.asString()))).toByteArray().also {
            val filePath = Key.key(customItem.key().namespace(), "items/${customItem.key().value()}").toPackPath("json")

            zipper.addFile(filePath, PackFile({ it }, it.size.toLong()))
        }
    }
}