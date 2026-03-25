package io.github.bindglam.neko.content

import io.github.bindglam.neko.registry.Registries
import io.github.bindglam.neko.utils.PLUGIN_ID
import net.kyori.adventure.key.Key
import java.io.File

class ContentsPackImpl(
    private val id: String,
    private val version: String,
    private val author: String,
    private val packFolder: File,
    private val registries: Registries,
) : ContentsPack {
    companion object {
        fun createKey(id: String) = Key.key(PLUGIN_ID, id)
    }

    override fun key() = createKey(id)
    override fun id() = id
    override fun version() = version
    override fun author() = author
    override fun packFolder() = packFolder
    override fun registries() = registries
}