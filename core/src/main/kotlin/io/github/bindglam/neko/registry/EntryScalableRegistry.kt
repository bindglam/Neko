package io.github.bindglam.neko.registry

import net.kyori.adventure.key.Key
import java.util.function.Consumer

class EntryScalableRegistry<T, E : EntryWritableRegistry.RegistryEntry<T>>(private val entrySupplier: () -> E) :
    ScalableRegistry<T>(), EntryWritableRegistry<T, E> {
    override fun register(key: Key, entry: Consumer<E>): T & Any {
        if(isLocked)
            error("The registry is locked")
        if(map.contains(key.asString()))
            error("The registry already contains the key")
        return entrySupplier().apply { entry.accept(this) }.toValue().also { map[key.asString()] = it }
    }
}