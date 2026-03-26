package io.github.bindglam.neko.content

import io.github.bindglam.neko.content.feature.FeatureBuilder
import net.kyori.adventure.key.Key

abstract class AbstractContent(protected val key: Key, features: List<FeatureBuilder>) : Content {
    protected val features = features.map { it.build(this) }

    override fun key() = key
    override fun features() = features
}