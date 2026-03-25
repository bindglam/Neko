package io.github.bindglam.neko.registry

import it.unimi.dsi.fastutil.Pair
import net.kyori.adventure.key.Key
import org.jetbrains.annotations.Unmodifiable
import java.util.Optional

class MappedRegistry<T>(data: Map<Key, T>) : Registry<T> {
    private val map = data.entries.stream().collect({ HashMap<String, T>() }, { map, entry -> map[entry.key.asString()] = entry.value }, { map1, map2 -> map1.putAll(map2) })

    override fun get(key: Key): Optional<T & Any> = Optional.ofNullable(map[key.asString()])

    override fun entries(): @Unmodifiable Collection<Pair<Key, T>> =
        map.entries.map { Pair.of(Key.key(it.key), it.value) }

    override fun iterator() = map.values.iterator()
}