package io.github.bindglam.neko.content.item

import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.content.item.properties.ItemProperties
import net.kyori.adventure.key.Key

class ItemRegistryEntryImpl : ItemRegistryEntry {
    private var key: Key? = null
    private var features: List<Feature>? = null
    private var properties: ItemProperties? = null

    override fun key(key: Key) = this.apply {
        this.key = key
    }

    override fun features(features: List<Feature>) = this.apply {
        this.features = features
    }

    override fun properties(properties: ItemProperties) = this.apply {
        this.properties = properties
    }

    override fun toValue() = ItemImpl(key ?: error("Key is null"), properties ?: error("Properties is null"), features ?: listOf())
}