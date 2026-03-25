package io.github.bindglam.neko.registry

import it.unimi.dsi.fastutil.Pair
import net.kyori.adventure.key.Key
import java.util.Optional

abstract class ScalableRegistry<T> : WritableRegistry<T> {
    protected val map = hashMapOf<String, T>()

    protected var isLocked = false

    @Synchronized
    override fun lock() {
        isLocked = true
    }

    @Synchronized
    override fun unlock() {
        isLocked = false
    }

    override fun merge(registry: Registry<T>) {
        if(isLocked)
            error("The registry is locked")

        val newMap = HashMap(map)
        for (entry in registry.entries()) {
            if(newMap.containsKey(entry.key().asString()))
                error("There is conflict with the registry")
            newMap[entry.key().asString()] = entry.value()
        }
        map.clear()
        map.putAll(newMap)
    }

    override fun get(key: Key) = Optional.ofNullable(map[key.asString()])

    override fun entries() = map.entries.map { entry -> Pair.of(Key.key(entry.key), entry.value) }

    override fun iterator() = map.values.iterator()
}