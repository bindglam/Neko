package io.github.bindglam.neko.content

import io.github.bindglam.neko.registry.Registries
import java.io.File

class ContentsPackRegistryEntryImpl : ContentsPackRegistryEntry {
    private var id: String? = null
    private var version: String? = null
    private var author: String? = null
    private var packFolder: File? = null
    private var registries: Registries? = null

    override fun id(id: String) = this.apply {
        this.id = id
    }

    override fun version(version: String) = this.apply {
        this.version = version
    }

    override fun author(author: String) = this.apply {
        this.author = author
    }

    override fun packFolder(packFolder: File) = this.apply {
        this.packFolder = packFolder
    }

    override fun registries(registries: Registries) = this.apply {
        this.registries = registries
    }

    override fun toValue() = ContentsPackImpl(
        id ?: error("Id is null"),
        version ?: error("Version is null"),
        author ?: error("Author is null"),
        packFolder ?: error("Pack folder is null"),
        registries ?: error("Registries is null")
    )
}