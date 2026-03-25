package io.github.bindglam.neko.content.feature.builtin

import io.github.bindglam.neko.content.feature.Feature
import io.github.bindglam.neko.content.feature.FeatureContext
import io.github.bindglam.neko.utils.PLUGIN_ID
import net.kyori.adventure.key.Key

object HelloWorldFeature : Feature {
    val KEY = Key.key(PLUGIN_ID, "hello_world")

    override fun init(context: FeatureContext.Init) {
        println("Hello ${context.content.key()}!")
    }

    override fun key() = KEY
}