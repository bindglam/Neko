package io.github.bindglam.neko;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.ClassPathLibrary;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public final class NekoPluginLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        classpathBuilder.addLibrary(mavenCentral());
        classpathBuilder.addLibrary(jitpack());
    }

    private static ClassPathLibrary mavenCentral() {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        RemoteRepository centralRepo = new RemoteRepository.Builder(
                "central", "default", "https://maven-central.storage-download.googleapis.com/maven2")
                .build();
        resolver.addRepository(centralRepo);

        resolver.addDependency(new Dependency(
                new DefaultArtifact("org.incendo:cloud-paper:2.0.0-beta.14"), null));
        resolver.addDependency(new Dependency(
                new DefaultArtifact("team.unnamed:creative-api:1.7.3"), null));
        resolver.addDependency(new Dependency(
                new DefaultArtifact("team.unnamed:creative-serializer-minecraft:1.7.3"), null));
        resolver.addDependency(new Dependency(
                new DefaultArtifact("team.unnamed:creative-server:1.7.3"), null));

        return resolver;
    }

    private static ClassPathLibrary jitpack() {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        RemoteRepository jitpackRepo = new RemoteRepository.Builder(
                "jitpack", "default", "https://jitpack.io")
                .build();
        resolver.addRepository(jitpackRepo);

        return resolver;
    }
}
