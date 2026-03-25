package io.github.bindglam.neko.content.feature.builtin

import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.content.feature.FeatureContext
import io.github.bindglam.neko.content.feature.FeatureFactory
import io.github.bindglam.neko.utils.PLUGIN_ID
import io.github.bindglam.neko.utils.logger
import net.kyori.adventure.key.Key

class HelloWorldFeature : Feature {
    companion object {
        val KEY = Key.key(PLUGIN_ID, "hello_world")
    }

    override fun init(context: FeatureContext.Init) {
        logger().info("Hello ${context.content().key().asString()}!")
    }

    override fun key() = KEY

    object Factory : FeatureFactory<HelloWorldFeature> {
        override fun create() = HelloWorldFeature()
    }
}