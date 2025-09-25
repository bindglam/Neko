package com.bindglam.neko

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.repository.RemoteRepository

class NekoLoader : PluginLoader {
    companion object {
        private const val MAVEN_CENTRAL_MIRROR = "https://maven-central.storage-download.googleapis.com/maven2"
    }

    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        classpathBuilder.addLibrary(MavenLibraryResolver().apply {
            try {
                val mirrorField = MavenLibraryResolver::class.java.getDeclaredField("MAVEN_CENTRAL_DEFAULT_MIRROR")
                addRepository(RemoteRepository.Builder("central", "default", mirrorField.get(null) as String).build())
            } catch (_: Exception) {
                addRepository(RemoteRepository.Builder("central", "default", MAVEN_CENTRAL_MIRROR).build())
            }
        })
    }
}