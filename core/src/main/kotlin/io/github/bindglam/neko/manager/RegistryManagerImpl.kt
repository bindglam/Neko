package io.github.bindglam.neko.manager

import io.github.bindglam.neko.content.ContentType
import io.github.bindglam.neko.content.ContentsPack
import io.github.bindglam.neko.content.feature.FeatureFactory
import io.github.bindglam.neko.content.item.ItemType
import io.github.bindglam.neko.event.RegistryInitializeEvent
import io.github.bindglam.neko.registry.DirectScalableRegistry
import io.github.bindglam.neko.registry.MappedRegistry
import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.registry.RegistriesImpl
import io.github.bindglam.neko.utils.logger

object RegistryManagerImpl : RegistryManager, Managerial, Reloadable {
    override fun preload(context: Context) {
        logger().info("Initializing registries...")

        GlobalRegistriesImpl.unlockAll()
        GlobalRegistriesImpl.clearAll()
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
        private val types = create(MappedRegistry<ContentType<*>>(mapOf(
            ItemType.KEY to ItemType
        )))
        private val features = create(DirectScalableRegistry<FeatureFactory<*>>())
        private val contentsPacks = create(DirectScalableRegistry<ContentsPack>())

        override fun types() = types
        override fun features() = features
        override fun contentsPacks() = contentsPacks

        override fun mergeAll(registries: Registries) {
            item().merge(registries.item())
        }
    }
}