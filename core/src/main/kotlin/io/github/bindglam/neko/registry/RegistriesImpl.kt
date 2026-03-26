package io.github.bindglam.neko.registry

import io.github.bindglam.neko.content.item.Item
import io.github.bindglam.neko.content.item.ItemRegistryEntry
import io.github.bindglam.neko.content.item.ItemRegistryEntryImpl

open class RegistriesImpl : Registries {
    protected val allRegistries = arrayListOf<Registry<*>>()

    private val item = create(EntryScalableRegistry<Item, ItemRegistryEntry> { ItemRegistryEntryImpl() })

    protected fun <T : Registry<*>> create(registry: T): T {
        allRegistries.add(registry)
        return registry
    }

    override fun item() = item

    override fun lockAll() {
        allRegistries.filterIsInstance<WritableRegistry<*>>().forEach { it.lock() }
    }

    override fun unlockAll() {
        allRegistries.filterIsInstance<WritableRegistry<*>>().forEach { it.unlock() }
    }

    override fun clearAll() {
        allRegistries.filterIsInstance<WritableRegistry<*>>().forEach { it.clear() }
    }
}