package io.github.bindglam.neko.content

import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.registry.WritableRegistry
import org.bukkit.configuration.ConfigurationSection

interface ContentType<T, E : WritableRegistry.RegistryEntry<T>> {
    val name: String

    fun load(registries: Registries, config: ConfigurationSection): LoadResult

    class LoadResult private constructor(val errorMsg: String?) {
        companion object {
            fun success() = LoadResult(null)
            fun failure(errorMsg: String) = LoadResult(errorMsg)
        }

        val isSuccess: Boolean
            get() = errorMsg == null
        val isFailure: Boolean
            get() = errorMsg != null
    }
}