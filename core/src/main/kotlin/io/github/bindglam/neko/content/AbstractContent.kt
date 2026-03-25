package io.github.bindglam.neko.content

import io.github.bindglam.neko.content.feature.Feature
import net.kyori.adventure.key.Key

abstract class AbstractContent(protected val key: Key, protected val features: List<Feature>) : Content {
    override fun key() = key
    override fun features() = features
}