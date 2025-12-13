package com.bindglam.neko.compatibility

interface Compatibility {
    val requiredPlugin: String

    fun start()

    fun end()
}