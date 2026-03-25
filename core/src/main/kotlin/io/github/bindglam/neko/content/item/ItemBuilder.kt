package io.github.bindglam.neko.content.item

import io.github.bindglam.neko.utils.PLUGIN_ID
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemBuilder {
    val NEKO_ITEM_KEY = NamespacedKey(PLUGIN_ID, "item")

    fun create(item: Item) = ItemStack(item.properties().type).apply {
        editMeta { meta ->
            meta.displayName((item.properties().name ?: Component.translatable(item).color(NamedTextColor.WHITE)).let { name ->
                if(name.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
                    name.decoration(TextDecoration.ITALIC, false)
                else
                    name
            })
            meta.lore(item.properties().lore)

            meta.persistentDataContainer.set(NEKO_ITEM_KEY, PersistentDataType.STRING, item.key().asString())
        }
    }
}