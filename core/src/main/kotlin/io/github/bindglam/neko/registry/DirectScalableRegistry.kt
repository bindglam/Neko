package io.github.bindglam.neko.registry

import net.kyori.adventure.key.Key

class DirectScalableRegistry<T> : ScalableRegistry<T>(), DirectWritableRegistry<T> {
    override fun register(key: Key, value: T & Any): T & Any {
        if(isLocked)
            error("The registry is locked")
        if(map.contains(key.asString()))
            error("The registry already contains the key")
        return value.also { map[key.asString()] = it }
    }
}