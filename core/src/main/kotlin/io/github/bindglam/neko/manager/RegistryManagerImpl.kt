package io.github.bindglam.neko.manager

import io.github.bindglam.neko.content.ContentType
import io.github.bindglam.neko.content.ContentsPack
import io.github.bindglam.neko.content.ContentsPackRegistryEntry
import io.github.bindglam.neko.content.ContentsPackRegistryEntryImpl
import io.github.bindglam.neko.content.item.Item
import io.github.bindglam.neko.content.item.ItemRegistryEntry
import io.github.bindglam.neko.content.item.ItemRegistryEntryImpl
import io.github.bindglam.neko.content.item.ItemType
import io.github.bindglam.neko.event.RegistryInitializeEvent
import io.github.bindglam.neko.registry.MappedRegistry
import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.registry.RegistriesImpl
import io.github.bindglam.neko.registry.Registry
import io.github.bindglam.neko.registry.ScalableRegistry
import io.github.bindglam.neko.registry.WritableRegistry
import io.github.bindglam.neko.utils.logger

object RegistryManagerImpl : RegistryManager, Managerial {
    override fun preload(context: Context) {
        logger().info("Initializing registries...")

        GlobalRegistriesImpl.unlockAll()
    }

    override fun start(context: Context) {
        RegistryInitializeEvent(GlobalRegistriesImpl).callEvent()

        GlobalRegistriesImpl.lockAll()

        logger().info("Successfully initialized registries!")
    }

    override fun end(context: Context) {
    }

    override fun registries() = GlobalRegistriesImpl

    object GlobalRegistriesImpl : RegistryManager.GlobalRegistries, RegistriesImpl() {
        private val types = MappedRegistry<ContentType<*>>(mapOf(
            ItemType.KEY to ItemType
        ))
        private val contentsPacks = ScalableRegistry<ContentsPack, ContentsPackRegistryEntry> { ContentsPackRegistryEntryImpl() }

        override fun types() = types
        override fun contentsPacks() = contentsPacks

        override fun mergeAll(registries: Registries) {
            item().merge(registries.item())
        }
    }
}