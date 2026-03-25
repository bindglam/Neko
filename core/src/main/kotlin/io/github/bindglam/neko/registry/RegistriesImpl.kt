package io.github.bindglam.neko.registry

import io.github.bindglam.neko.content.item.Item
import io.github.bindglam.neko.content.item.ItemRegistryEntry
import io.github.bindglam.neko.content.item.ItemRegistryEntryImpl

open class RegistriesImpl : Registries {
    private val item = EntryScalableRegistry<Item, ItemRegistryEntry> { ItemRegistryEntryImpl() }

    override fun item() = item

    override fun lockAll() {
        item.lock()
    }

    override fun unlockAll() {
        item.unlock()
    }
}