package io.github.bindglam.neko

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.ClassPathLibrary
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository

@Suppress("UnstableApiUsage")
class NekoPluginLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        classpathBuilder.addLibrary(mavenCentral())
        classpathBuilder.addLibrary(jitpack())
    }

    private fun mavenCentral(): ClassPathLibrary {
        val resolver = MavenLibraryResolver()

        resolver.addRepository(RemoteRepository.Builder("central", "default", "https://maven-central.storage-download.googleapis.com/maven2").build())

        resolver.addDependency(Dependency(DefaultArtifact("org.incendo:cloud-paper:2.0.0-beta.14"), null))
        resolver.addDependency(Dependency(DefaultArtifact("team.unnamed:creative-api:1.7.3"), null))
        resolver.addDependency(Dependency(DefaultArtifact("team.unnamed:creative-serializer-minecraft:1.7.3"), null))
        resolver.addDependency(Dependency(DefaultArtifact("team.unnamed:creative-server:1.7.3"), null))

        return resolver
    }

    private fun jitpack(): ClassPathLibrary {
        val resolver = MavenLibraryResolver()

        resolver.addRepository(RemoteRepository.Builder("jitpack", "default", "https://jitpack.io").build())

        return resolver
    }
}