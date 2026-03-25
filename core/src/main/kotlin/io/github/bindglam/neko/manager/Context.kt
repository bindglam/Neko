package io.github.bindglam.neko.manager

import io.github.bindglam.neko.NekoPluginImpl

class Context(private val plugin: NekoPluginImpl) {
    fun plugin() = plugin
}