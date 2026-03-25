package io.github.bindglam.neko.content

import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.content.feature.FeatureContext
import net.kyori.adventure.key.Key

abstract class AbstractContent(protected val key: Key, protected val features: List<Feature>) : Content {
    init {
        features.forEach { it.init(FeatureContext.Init(this)) }
    }

    override fun key() = key
    override fun features() = features
}