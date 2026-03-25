package io.github.bindglam.neko.registry

import it.unimi.dsi.fastutil.Pair
import net.kyori.adventure.key.Key
import java.util.Optional
import java.util.function.Consumer

class ScalableRegistry<T, E : WritableRegistry.RegistryEntry<T>>(private val entrySupplier: () -> E) : WritableRegistry<T, E> {
    private val map = hashMapOf<String, T>()

    private var isLocked = false

    @Synchronized
    override fun lock() {
        isLocked = true
    }

    @Synchronized
    override fun unlock() {
        isLocked = false
    }

    override fun register(key: Key, entry: Consumer<E>): T & Any {
        if(isLocked)
            error("The registry is locked")
        if(map.contains(key.asString()))
            error("The registry already contains the key")
        return entrySupplier().apply { entry.accept(this) }.toValue().also { map[key.asString()] = it }
    }

    override fun register(key: Key, value: T & Any): T & Any {
        if(isLocked)
            error("The registry is locked")
        if(map.contains(key.asString()))
            error("The registry already contains the key")
        return value.also { map[key.asString()] = it }
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