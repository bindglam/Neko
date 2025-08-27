package com.bindglam.neko

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository



@Suppress("UnstableApiUsage")
class NekoLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        classpathBuilder.addLibrary(MavenLibraryResolver().apply {
            addRepository(RemoteRepository.Builder("central", "default", MavenLibraryResolver.MAVEN_CENTRAL_DEFAULT_MIRROR).build())
        })
    }
}