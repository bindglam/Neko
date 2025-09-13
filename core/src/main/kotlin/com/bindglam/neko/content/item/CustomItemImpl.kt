package com.bindglam.neko.content.item

import com.bindglam.neko.api.content.item.CustomItem
import com.bindglam.neko.api.content.item.CustomItemProperties
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.pack.item.ItemData
import com.bindglam.neko.utils.toPackPath
import com.google.gson.Gson
import de.tr7zw.changeme.nbtapi.NBT
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

open class CustomItemImpl(private val key: NamespacedKey, private val properties: CustomItemProperties) : CustomItem {
    companion object {
        const val ITEM_KEY_TAG = "neko-item"

        private val GSON = Gson()
    }

    override fun pack(zipper: PackZipper) {
        val modelPath = properties.model() ?: return

        GSON.toJson(ItemData(ItemData.Model(ItemData.Type.MODEL, modelPath.asString()))).toByteArray().also {
            val filePath = Key.key(key.namespace(), "items/${key.value()}").toPackPath("json")

            zipper.addFile(filePath, PackFile({ it }, it.size.toLong()))
        }
    }

    override fun itemProperties(): CustomItemProperties = properties

    override fun key(): Key = key

    override fun itemStack(): ItemStack = properties.type().createItemStack().apply {
        itemMeta = itemMeta.apply {
            itemName(properties.name())
            lore(properties.lore())
            itemModel = if(properties.model() != null) key else null
        }

        NBT.modify(this) { nbt ->
            nbt.setString(ITEM_KEY_TAG, key.asString())
        }
    }

    override fun isSame(other: ItemStack?): Boolean {
        other ?: return false

        if(other.amount == 0 || other.type == Material.AIR)
            return false

        val nbt = NBT.readNbt(other)

        if(!nbt.hasTag(ITEM_KEY_TAG))
            return false

        return key.asString() == nbt.getString(ITEM_KEY_TAG)
    }
}