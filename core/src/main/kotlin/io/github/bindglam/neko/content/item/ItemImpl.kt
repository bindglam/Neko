package io.github.bindglam.neko.content.item

import io.github.bindglam.neko.content.item.properties.ItemProperties
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class ItemImpl(private val key: Key, private val properties: ItemProperties) : Item {
    private val itemStack = ImmutableItemStack.of(ItemBuilder.create(this))

    override fun isSimilar(itemStack: ItemStack): Boolean {
        if(!itemStack.hasItemMeta()) return false
        return itemStack.itemMeta.persistentDataContainer.get(ItemBuilder.NEKO_ITEM_KEY, PersistentDataType.STRING) == key.asString()
    }

    override fun properties() = properties
    override fun itemStack() = itemStack
    override fun key() = key
    override fun translationKey() = "item.${key.namespace()}.${key.value()}"
    override fun asItem() = this
}